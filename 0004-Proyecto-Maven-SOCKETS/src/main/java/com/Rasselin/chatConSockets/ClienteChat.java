package com.Rasselin.chatConSockets;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Clase principal: pide nick y abre la ventana del cliente
public class ClienteChat {
    public static void main(String[] args) {
        String nickNombre = "";
        do {
            nickNombre = JOptionPane.showInputDialog(
                    "Introduzca su nombre para conectar con atención al cliente", "IDENTIFÍQUESE");
        } while (nickNombre == null || nickNombre.isEmpty()
                || nickNombre.equals("IDENTIFÍQUESE") || nickNombre.length() < 5);

        MarcoClienteChat miMarco = new MarcoClienteChat(nickNombre);
    }
}

// Ventana del cliente
class MarcoClienteChat extends JFrame {
    private Image icono;
    private InterfazCliente clientePanel;

    public MarcoClienteChat(String nombre) {
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);
        setTitle("VENTANA CLIENTE");
        setBounds(300, 100, 350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.clientePanel = new InterfazCliente(nombre);
        add(clientePanel);
        setVisible(true);
    }
}

// Panel del cliente: UI + lógica de conexión
class InterfazCliente extends JPanel implements ActionListener, Runnable {

    private JLabel etiquetaEnvio;
    private JButton botonEnviar;
    private JTextField campoTexto;

    private JLabel etiquetaRecepcion;
    private JTextArea areaRespuesta;

    private JLabel etiquetaNick;
    private JLabel etiquetaNickValor;

    private JLabel etiquetaConectarCon;
    private JComboBox<String> comboDestinatarios;

    private final String nombreUsuario;

    // Socket y streams persistentes
    private Socket socketServidor;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public InterfazCliente(String nombre) {
        this.nombreUsuario = nombre;

        setLayout(null);

        this.etiquetaEnvio = new JLabel("TEXTO PARA ENVIAR");
        this.botonEnviar = new JButton("ENVIAR");
        this.campoTexto = new JTextField();

        this.etiquetaRecepcion = new JLabel("TEXTO RECIBIDO");
        this.areaRespuesta = new JTextArea();

        this.etiquetaNick = new JLabel("NICK DE CONEXIÓN:");
        this.etiquetaNickValor = new JLabel(this.nombreUsuario);

        this.etiquetaConectarCon = new JLabel("CONECTAR CON:");
        this.comboDestinatarios = new JComboBox<>();

        // Posicionamiento
        etiquetaEnvio.setBounds(50, 15, 120, 20);
        campoTexto.setBounds(50, 40, 200, 20);
        botonEnviar.setBounds(50, 70, 100, 20);

        etiquetaRecepcion.setBounds(50, 100, 120, 20);
        areaRespuesta.setBounds(50, 130, 200, 200);

        etiquetaNick.setBounds(50, 350, 200, 20);
        etiquetaNickValor.setBounds(50, 370, 200, 20);

        etiquetaConectarCon.setBounds(50, 400, 200, 20);
        comboDestinatarios.setBounds(50, 420, 200, 20);

        botonEnviar.addActionListener(this);

        add(etiquetaEnvio);
        add(campoTexto);
        add(botonEnviar);
        add(etiquetaRecepcion);
        add(areaRespuesta);
        add(etiquetaNick);
        add(etiquetaNickValor);
        add(etiquetaConectarCon);
        add(comboDestinatarios);
        
        // Hilo para recibir mensajes del servidor
        Thread hiloRecepcion = new Thread(this);
        hiloRecepcion.start();
        // Establece conexión con el servidor
        establecerConexion();
    }

    // Conecta al servidor y envía mensaje "online"
    private void establecerConexion() {
        try {
            // IP del servidor y puerto (ajusta según tu red)
            socketServidor = new Socket("192.168.1.137", 9999);

            salida = new ObjectOutputStream(socketServidor.getOutputStream());
            entrada = new ObjectInputStream(socketServidor.getInputStream());

            // Paquete de conexión inicial
            PaqueteMensaje datosHost = new PaqueteMensaje();
            datosHost.setMensaje("online");
            datosHost.setNick(this.nombreUsuario);

            salida.writeObject(datosHost);
            salida.flush();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error conectando al servidor: " + e.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Enviar mensaje al servidor
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (socketServidor == null || socketServidor.isClosed()) {
                JOptionPane.showMessageDialog(this,
                        "No hay conexión con el servidor.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String texto = campoTexto.getText();
            if (texto == null || texto.isEmpty()) return;

            // Destinatario seleccionado (nick)
            String nickDestino = (String) comboDestinatarios.getSelectedItem();

            PaqueteMensaje mensaje = new PaqueteMensaje();
            mensaje.setNick(this.nombreUsuario);
            mensaje.setMensaje(texto);

            // Opcional: podrías usar nickDestino para filtrar en el servidor
            // Aquí simplemente se envía el mensaje al servidor

            salida.writeObject(mensaje);
            salida.flush();

            campoTexto.setText("");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error enviando mensaje: " + ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hilo de recepción: escucha mensajes del servidor
    @Override
    public void run() {
        try {
            // Espera activa hasta que el socket y los streams estén listos
            while (socketServidor == null || entrada == null) {
                Thread.sleep(10);
            }

            // Ahora sí: empieza a recibir paquetes
            while (true) {
                PaqueteMensaje paqueteRecibido = (PaqueteMensaje) entrada.readObject();

                if ("online".equals(paqueteRecibido.getMensaje())) {
                    comboDestinatarios.removeAllItems();
                    for (String nick : paqueteRecibido.getListaNicks()) {
                        comboDestinatarios.addItem(nick);
                    }
                } else {
                    areaRespuesta.append("\n" + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Paquete de datos que viaja entre cliente y servidor
class PaqueteMensaje implements Serializable {
    private String nick = "";
    private String IP = "";
    private String mensaje = "";
    private static final long serialVersionUID = 1L;
    private ArrayList<String> listaIps;
    private ArrayList<String> listaNicks;
    private HashMap<String, String> mapa;

    public PaqueteMensaje() {}

    public PaqueteMensaje(String nick, String IP, String mensaje) {
        this.nick = nick;
        this.IP = IP;
        this.mensaje = mensaje;
    }

    public ArrayList<String> getListaNicks() {
        return listaNicks;
    }

    public ArrayList<String> getListaIps() {
        return listaIps;
    }

    public void setListaNicks(ArrayList<String> listaNicks) {
        this.listaNicks = listaNicks;
    }

    public void setListaIps(ArrayList<String> listaIps) {
        this.listaIps = listaIps;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Rellena el HashMap con NICK -> IP
    public void rellenoHashMap(ArrayList<String> IPS, ArrayList<String> NICKS) {
        this.mapa = new HashMap<>();
        for (int i = 0; i < IPS.size(); i++) {
            this.mapa.put(NICKS.get(i), IPS.get(i));
        }
    }

    // Devuelve la IP asociada a un nick
    public String devuelveValor(String clave) {
        if (this.mapa == null) return null;
        return this.mapa.get(clave);
    }
}
