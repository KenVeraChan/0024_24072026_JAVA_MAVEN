package com.Rasselin.sockets;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SocketsConexion {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Cliente ventanaCliente= new Cliente();
        Servidor ventanaServidor= new Servidor();
    }
}


class Cliente extends JFrame
{
	private Image icono;
	private InterfazCliente cliente1;
	public Cliente()
	{
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA CLIENTE");
		setBounds(200,200,300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.cliente1= new InterfazCliente();
        add(cliente1);
        setVisible(true);
	}
}

class InterfazCliente extends JPanel implements ActionListener
{
	private JLabel cajaEnvio;
	private JButton enviarMensaje;
	private JTextField cajaTexto;
	
	public InterfazCliente()
	{
		setLayout(null);  //Para que respeten el setBounds
		this.cajaEnvio= new JLabel("TEXTO PARA ENVIAR");
		this.enviarMensaje= new JButton("ENVIAR");
		this.cajaTexto= new JTextField();
		enviarMensaje.setBounds(50, 100, 100, 20);
		cajaEnvio.setBounds(50, 50, 120, 20);
		cajaTexto.setBounds(50, 80, 120, 20);
		
		enviarMensaje.addActionListener(this);
		add(enviarMensaje);
		add(cajaEnvio);
		add(cajaTexto);
	}

	public void actionPerformed(ActionEvent e) {
		//Aqui debería gestionarse el envío del mensaje
		try {
			//IP--> CMD ---> ipconfig ---> Ipv4 y el puerto el que sea valdra
			//Creacion de SOCKET via de comunicacion
			Socket tunelComunicacion= new Socket("172.19.112.1",9999);
			
			//Creacion del flujo de datos
			//Se le dice por donde debe el flujo de datos, por donde ira de que tunel de datos
			DataOutputStream flujoDatosSalida= new DataOutputStream(tunelComunicacion.getOutputStream());
			flujoDatosSalida.writeUTF(cajaTexto.getText());
			flujoDatosSalida.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
	}

}
class Servidor extends JFrame
{
	private Image icono;
	private InterfazServidor servidor1;
	
	public Servidor()
	{
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA SERVIDOR");
		setBounds(800,200,300,300);
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
		cajaTexto.setBounds(50, 70, 120, 200);

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
			// PUERTO DE ESCUCHA DEL SERVIDOR y se deja creado
			ServerSocket receptor= new ServerSocket(9999);
			
			while(true)
			{
				// Se aceptan todas las comunicaciones que vengan por este tunel
				Socket entrada= receptor.accept();
				
				// Se fijan el flujo de datos de entrada
				DataInputStream flujoDatos= new DataInputStream(entrada.getInputStream());
				
				//Variable que correspondera la comunicacion recibida
				String datoEntrada= flujoDatos.readUTF();
				
				//Se ubica en el area de texto de la ventana del servidor
				cajaTexto.append(datoEntrada.concat("\n"));
				
				//Cierre de SOCKET para cerrar
				entrada.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}