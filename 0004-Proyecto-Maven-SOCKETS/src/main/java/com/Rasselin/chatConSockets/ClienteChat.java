package com.Rasselin.chatConSockets;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

public class ClienteChat
{
	public static void main(String[] args)
	{
		MarcoClienteChat miMarco= new MarcoClienteChat();
	}
}

class MarcoClienteChat extends JFrame
{
	private Image icono;
	private InterfazCliente cliente1;
	private int ventanas;
	
	public MarcoClienteChat()
	{
        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA CLIENTE");
		setBounds(100,150,350,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.cliente1= new InterfazCliente();
        add(cliente1);
        setVisible(true);
	}
}

class InterfazCliente extends JPanel implements ActionListener, Runnable
{
	private JLabel cajaEnvio;
	private JButton enviarMensaje;
	private JTextField cajaTexto;
	
	private JLabel cajaRecepcion;
	private JTextArea cajaTextoRespuesta;
	
	private JLabel Titulonick; 
	private JTextField Cajanick;
	
	private JLabel TituloIPpersonaConec; 
	private JTextField CajaIPpersonaConec;
	
	public InterfazCliente()
	{
		setLayout(null);  //Para que respeten el setBounds
		this.Titulonick= new JLabel("NICK CONEXION:");
		this.Cajanick= new JTextField();
		
		this.TituloIPpersonaConec= new JLabel("CONECTAR CON:");
		this.CajaIPpersonaConec= new JTextField();
		
		this.cajaEnvio= new JLabel("TEXTO PARA ENVIAR");
		this.enviarMensaje= new JButton("ENVIAR");
		this.cajaTexto= new JTextField();
		
		this.cajaRecepcion= new JLabel("TEXTO RECIBIDIO");
		this.cajaTextoRespuesta= new JTextArea();
		
		this.cajaEnvio.setBounds(50, 15, 120, 20);
		this.cajaTexto.setBounds(50, 40, 200, 20);
		this.enviarMensaje.setBounds(50, 70, 100, 20);
		
		this.cajaRecepcion.setBounds(50,100,120,20);
		this.cajaTextoRespuesta.setBounds(50,130,200,200);
		
		this.Titulonick.setBounds(50,360,200,20);
		this.Cajanick.setBounds(50,390,100,20);
		
		this.TituloIPpersonaConec.setBounds(180,360,200,20);
		this.CajaIPpersonaConec.setBounds(180,390,100,20);
		
		this.enviarMensaje.addActionListener(this);
		
		add(enviarMensaje);
		add(cajaEnvio);
		add(cajaTexto);
		add(cajaRecepcion);
		add(cajaTextoRespuesta);
		add(Titulonick);
		add(Cajanick);
		add(TituloIPpersonaConec);
		add(CajaIPpersonaConec);
		
		//Se crea un hilo sobre el panel
		Thread hilo= new Thread(this);
		hilo.start();
	}

	public void actionPerformed(ActionEvent e) {
		//Aqui debería gestionarse el envío del mensaje
		try {
			//IP--> CMD ---> ipconfig ---> Ipv4 y el puerto el que sea valdra
			//Creacion de SOCKET, OUTPUTSTREAM, PAQUETE MENSAJE PARA ENVIAR
			
			//ENVIANDO INFORMACION A TRAVÉS DE UN SOCKET, UN FLUJO DE TRANSMISION OUTPUTSTREAM Y UN OBJETO MENSAJE
			Socket tunelComunicacion= new Socket("192.168.1.136",9999);
			    //IP donde se ejecuta el SERVIDOR, si el servidor esta en una maquina virtual o física 
			    // es posible que la IP cambie cada vez que se encienda, luego se deberá cambiar esa IP
			    // por la nueva que el sistema operativo posea haciendo CMD----> ipconfig -----> Direccion IPv4
			
			PaqueteMensaje mensaje= new PaqueteMensaje();
			mensaje.setIP(CajaIPpersonaConec.getText());
			mensaje.setNick(Cajanick.getText());
			mensaje.setMensaje(cajaTexto.getText());
			
			ObjectOutputStream envioPaquete= new ObjectOutputStream(tunelComunicacion.getOutputStream());
			envioPaquete.writeObject(mensaje);
			envioPaquete.flush();  // Buena práctica: asegura el envío de los datos
			envioPaquete.close();  //CIERRA EL FLUJO DE DATOS
			tunelComunicacion.close(); //CIERRA EL SOCKET DE COMUNICACION
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showInputDialog("CAUSA: "+e1.getCause()+"\n ERROR: "+e1.getMessage(),"ERROR");
		}  
	}

	@Override
	public void run() {
		try {
			//ENTRADA: TUNEL, FLUJO DE DATOS Y PAQUETE DE ENTRADA EN EL CLIENTE
			ServerSocket escuchaCliente = new ServerSocket(9090, 50, java.net.InetAddress.getByName("0.0.0.0"));
			Socket cliente;
			PaqueteMensaje paqueteRecibido;
			while(true)
			{
				cliente=escuchaCliente.accept();   //Acepta todas las conexiones
				ObjectInputStream flujoEntrada= new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido=(PaqueteMensaje)flujoEntrada.readObject();
				cajaTextoRespuesta.append("\n"+paqueteRecibido.getNick()+"\n"+paqueteRecibido.getMensaje());
				flujoEntrada.close();  //CIERRA EL FLUJO DE DATOS
				cliente.close();  //CIERRA EL SOCKET DE COMUNICACION
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showInputDialog("CAUSA: "+e.getCause()+"\n ERROR: "+e.getMessage(),"ERROR");

		}
	}
}

class PaqueteMensaje implements Serializable
{
	private String nick="";
	private String IP="";
	private String mensaje="";
	private static final long serialVersionUID = 1L;
	
	public PaqueteMensaje()
	{
		
	}
	
	public PaqueteMensaje(String nick,String IP,String mensaje)
	{
		this.nick=nick;
		this.IP=IP;
		this.mensaje=mensaje;
		
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
}