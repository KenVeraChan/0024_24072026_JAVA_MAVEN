package com.Rasselin.chatConSockets;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
			
			while(true)
			{
				//SIEMPRE ESCUCHANDO
				// Se aceptan todas las comunicaciones que vengan por este tunel
				Socket entrada= receptor.accept();
				
				// Se fijan el flujo de datos de entrada
				ObjectInputStream flujoDatos= new ObjectInputStream(entrada.getInputStream());
				
				//Variable que correspondera la comunicacion recibida
				try {
					//ENTRADA: TUNEL, FLUJO DE DATOS Y PAQUETE DE ENTRADA EN EL SERVIDOR
					paqueteRecibido= (PaqueteMensaje)flujoDatos.readObject();
					nick=paqueteRecibido.getNick();
					ip=paqueteRecibido.getIP();
					mensaje=paqueteRecibido.getMensaje();
					cajaTexto.append("Se ha conectado "+nick+"\n con su IP: "+ip+"\n y su mensaje: "+mensaje);
				
					//SALIDA: TUNEL, FLUJO DE DATOS Y PAQUETE DE SALIDA EN EL SERVIDOR
					//Se ubica en el area de texto de la ventana del servidor
					Socket reenvio= new Socket(ip,9090);
					ObjectOutputStream paqueteReenvio= new ObjectOutputStream(reenvio.getOutputStream());
					paqueteReenvio.writeObject(paqueteRecibido);
					paqueteReenvio.flush(); // Buena práctica: asegura el envío de los datos
					paqueteReenvio.close();  //CIERRA EL FLUJO DE DATOS
					reenvio.close();  //CIERRA EL SOCKET DE COMUNICACION
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showInputDialog("CAUSA: "+e.getCause()+"\n ERROR: "+e.getMessage(),"ERROR");
				}		
				flujoDatos.close(); //CIERRA EL FLUJO DE DATOS
				entrada.close(); //CIERRA EL SOCKET DE COMUNICACION
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showInputDialog("CAUSA: "+e.getCause()+"\n ERROR: "+e.getMessage(),"ERROR");
		}
	}
}