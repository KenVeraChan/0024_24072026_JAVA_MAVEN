package com.Rasselin.chatConSockets;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ServidorChat{
	public static void main(String[] args)
	{
		MarcoServidorChat ventanaServidor= new MarcoServidorChat();
	}
}

class MarcoServidorChat extends JFrame
{
	private Image icono;
	private InterfazServidor servidor1;
	
	public MarcoServidorChat()
	{
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA SERVIDOR");
		setBounds(500,150,300,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.servidor1= new InterfazServidor();
        add(servidor1);
        setVisible(true);
	}
}

class InterfazServidor extends JPanel implements ActionListener,Runnable
{
	private JLabel cajaEnvio;
	private JTextArea cajaTexto;
	
	public InterfazServidor()
	{
		setLayout(null);  //Para que respeten el setBounds
		this.cajaEnvio= new JLabel("TEXTO RECIBIDO");
		this.cajaTexto= new JTextArea();
		cajaEnvio.setBounds(50, 50, 120, 20);
		cajaTexto.setBounds(50, 70, 200, 300);

		add(cajaEnvio);
		add(cajaTexto);
		
        //Creacion de hilo (thread)
        Thread miHilo= new Thread(this);
        miHilo.start();
	}

	public void actionPerformed(ActionEvent e) {
		//Aqui debería gestionarse el envío del mensaje
		
	}
	public void run() 
	{
		try {
			// PUERTO DE ESCUCHA DEL SERVIDOR Y SE DEJA CREADO
			ServerSocket receptor= new ServerSocket(9999);
			PaqueteMensaje paqueteRecibido;
			String nick,ip,mensaje;
			ArrayList<String> listaIps= new ArrayList<String>();  //Se creará una unica vez para el registro de las IPS
			
			while(true)
			{
				//SIEMPRE ESCUCHANDO
				// Se aceptan todas las comunicaciones que vengan por este tunel
				Socket entrada= receptor.accept();
				
				// Se fijan el flujo de datos de entrada
				ObjectInputStream flujoDatos= new ObjectInputStream(entrada.getInputStream());
				
				//Variable que correspondera la comunicacion recibida
					//ENTRADA: TUNEL, FLUJO DE DATOS Y PAQUETE DE ENTRADA EN EL SERVIDOR
					try {
						paqueteRecibido= (PaqueteMensaje)flujoDatos.readObject();

						nick=paqueteRecibido.getNick();
						ip=paqueteRecibido.getIP();
						mensaje=paqueteRecibido.getMensaje();
						
						if(!mensaje.equals("online"))
						{
							cajaTexto.append("Se ha conectado "+nick+"\n con su IP: "+ip+"\n y su mensaje: "+mensaje);
							//Si el primer mensaje no es online
							//SALIDA: TUNEL, FLUJO DE DATOS Y PAQUETE DE SALIDA EN EL SERVIDOR
							//Se ubica en el area de texto de la ventana del servidor
							Socket reenvio= new Socket(ip,9090);
							ObjectOutputStream paqueteReenvio= new ObjectOutputStream(reenvio.getOutputStream());
							paqueteReenvio.writeObject(paqueteRecibido);
							paqueteReenvio.flush(); // Buena práctica: asegura el envío de los datos
							paqueteReenvio.close();  //CIERRA EL FLUJO DE DATOS
							reenvio.close();  //CIERRA EL SOCKET DE COMUNICACION
							
							flujoDatos.close(); //CIERRA EL FLUJO DE DATOS
							entrada.close(); //CIERRA EL SOCKET DE COMUNICACION
						}
						else
						{
							InetAddress direccionCliente= entrada.getInetAddress();
							String ipCliente= direccionCliente.getHostAddress();
							System.out.println("El cliente con IP: "+ipCliente+" se ha conectado");
							listaIps.add(ipCliente);
							
							paqueteRecibido.setListaIps(listaIps);
							
							for(String IpConectado: listaIps) 
								{ 
									System.out.println("El usuario conectado es: "+IpConectado);
								
									//Por cada vuelta de bucle le envíe el ARRAYLIST con todos los clientes conectados
									// Cuantos mas se conecten más paquetes de datos se enviarán
									// Viaja el Nick, IP, textoMensaje enviado, se envían en un bloque ArrayList
									Socket reenvio= new Socket(IpConectado,9090);
									ObjectOutputStream paqueteReenvio= new ObjectOutputStream(reenvio.getOutputStream());
									paqueteReenvio.writeObject(paqueteRecibido);
									paqueteReenvio.flush(); // Buena práctica: asegura el envío de los datos
									paqueteReenvio.close();  //CIERRA EL FLUJO DE DATOS
									reenvio.close();  //CIERRA EL SOCKET DE COMUNICACION
								}
						}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showInputDialog("CAUSA: "+e.getCause()+"\n ERROR: "+e.getMessage(),"ERROR");
		}
	}
}