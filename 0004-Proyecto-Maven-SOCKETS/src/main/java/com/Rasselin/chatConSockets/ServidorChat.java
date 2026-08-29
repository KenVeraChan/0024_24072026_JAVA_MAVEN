package com.Rasselin.chatConSockets;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Clase principal: lanza la ventana del servidor
public class ServidorChat {
    public static void main(String[] args) {
        MarcoServidorChat ventanaServidor = new MarcoServidorChat();
    }
}

// Ventana gráfica del servidor
class MarcoServidorChat extends JFrame {
    private Image icono;
    private InterfazServidor servidorPanel;

    public MarcoServidorChat() {
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);
        setTitle("VENTANA SERVIDOR");
        setBounds(200, 50, 900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.servidorPanel = new InterfazServidor();
        add(servidorPanel);
        setVisible(true);
    }
}

// Panel del servidor: muestra texto y lanza el hilo de escucha
class InterfazServidor extends JPanel implements Runnable {

    private JLabel etiquetaTexto;
    private JTextArea areaTexto;

    // Lista de clientes conectados (cada uno con su socket y streams)
    private final ArrayList<ClienteConectado> clientesConectados = new ArrayList<>();

    public InterfazServidor() {
        setLayout(null);
        this.etiquetaTexto = new JLabel("TEXTO RECIBIDO");
        this.areaTexto = new JTextArea();

        etiquetaTexto.setBounds(50, 50, 120, 20);
        areaTexto.setBounds(50, 70, 800, 400);

        add(etiquetaTexto);
        add(areaTexto);

        // Hilo del servidor: acepta conexiones
        Thread hiloServidor = new Thread(this);
        hiloServidor.start();
    }

    @Override
    public void run() {
        try {
            // Servidor escucha en el puerto 9999
            ServerSocket servidor = new ServerSocket(9999);
            areaTexto.append("Servidor escuchando en puerto 9999...\n");

            while (true) {
                // Acepta nueva conexión de cliente
                Socket socketCliente = servidor.accept();

                // Crea streams para ese cliente
                ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());

                // Crea objeto que representa al cliente conectado
                ClienteConectado cliente = new ClienteConectado(socketCliente, entrada, salida);

                // Lanza un hilo para gestionar a ese cliente
                Thread hiloCliente = new Thread(new ManejadorCliente(cliente, this));
                hiloCliente.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            areaTexto.append("ERROR en servidor: " + e.getMessage() + "\n");
        }
    }

    // Añade un cliente a la lista de conectados
    public synchronized void registrarCliente(ClienteConectado cliente) {
        clientesConectados.add(cliente);
        areaTexto.append("Cliente registrado: " + cliente.getNick() + " (" + cliente.getIp() + ")\n");
        actualizarListaClientes();
    }

    // Elimina un cliente de la lista (por desconexión)
    public synchronized void eliminarCliente(ClienteConectado cliente) {
        clientesConectados.remove(cliente);
        areaTexto.append("Cliente desconectado: " + cliente.getNick() + " (" + cliente.getIp() + ")\n");
        actualizarListaClientes();
    }

    // Actualiza la lista de IPs y Nicks y la envía a todos los clientes
    private synchronized void actualizarListaClientes() {
        ArrayList<String> listaIps = new ArrayList<>();
        ArrayList<String> listaNicks = new ArrayList<>();

        for (ClienteConectado c : clientesConectados) {
            listaIps.add(c.getIp());
            listaNicks.add(c.getNick());
        }

        // Crea un paquete de tipo "online" con la lista de clientes
        PaqueteMensaje paqueteOnline = new PaqueteMensaje();
        paqueteOnline.setMensaje("online");
        paqueteOnline.setListaIps(listaIps);
        paqueteOnline.setListaNicks(listaNicks);
        paqueteOnline.rellenoHashMap(listaIps, listaNicks);

        // Envía el paquete a todos los clientes conectados
        broadcast(paqueteOnline);
    }

    // Envía un paquete a todos los clientes conectados
    public synchronized void broadcast(PaqueteMensaje paquete) {
        for (ClienteConectado c : clientesConectados) {
            try {
                c.getSalida().writeObject(paquete);
                c.getSalida().flush();
            } catch (IOException e) {
                areaTexto.append("Error enviando a " + c.getNick() + ": " + e.getMessage() + "\n");
            }
        }
    }

    // Muestra un mensaje recibido en el área de texto del servidor
    public void mostrarMensajeServidor(String texto) {
        areaTexto.append(texto + "\n");
    }
}

// Clase que representa a un cliente conectado al servidor
class ClienteConectado {
    private final Socket socket;
    private final ObjectInputStream entrada;
    private final ObjectOutputStream salida;
    private String nick;
    private String ip;

    public ClienteConectado(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.ip = socket.getInetAddress().getHostAddress();
        this.nick = "DESCONOCIDO";
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    public ObjectOutputStream getSalida() {
        return salida;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }
}

// Hilo que gestiona a un cliente concreto
class ManejadorCliente implements Runnable {

    private final ClienteConectado cliente;
    private final InterfazServidor servidorUI;

    public ManejadorCliente(ClienteConectado cliente, InterfazServidor servidorUI) {
        this.cliente = cliente;
        this.servidorUI = servidorUI;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Lee un paquete del cliente
                PaqueteMensaje paquete = (PaqueteMensaje) cliente.getEntrada().readObject();

                // Si el mensaje es "online", es una conexión inicial
                if ("online".equals(paquete.getMensaje())) {
                    cliente.setNick(paquete.getNick());
                    servidorUI.mostrarMensajeServidor(
                            "Cliente conectado: " + cliente.getNick() + " (" + cliente.getIp() + ")");
                    servidorUI.registrarCliente(cliente);
                } else {
                    // Es un mensaje normal de chat
                    String texto = "Mensaje de " + paquete.getNick() + " -> " + paquete.getMensaje();
                    servidorUI.mostrarMensajeServidor(texto);

                    // Reenvía el mensaje a todos los clientes
                    servidorUI.broadcast(paquete);
                }
            }
        } catch (Exception e) {
            // Si hay error (desconexión, etc.), se elimina el cliente
            servidorUI.eliminarCliente(cliente);
            try {
                cliente.getEntrada().close();
                cliente.getSalida().close();
                cliente.getSocket().close();
            } catch (IOException ex) {
                // Ignorar errores de cierre
            }
        }
    }
}
