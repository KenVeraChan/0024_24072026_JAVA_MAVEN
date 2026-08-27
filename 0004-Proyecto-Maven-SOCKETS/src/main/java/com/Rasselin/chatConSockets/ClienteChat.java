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
import java.net.UnknownHostException;
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

public class ClienteChat
{
	public static void main(String[] args)
	{
		String nickNombre="";
		do {
		nickNombre= JOptionPane.showInputDialog("Introduzca su nombre para conectar con atención al cliente","IDENTIFÍQUESE");
		}while(nickNombre==null || nickNombre.equals("") || nickNombre.equals("IDENTIFÍQUESE") || nickNombre.length()<5);
		MarcoClienteChat miMarco= new MarcoClienteChat(nickNombre);
	}
}

class MarcoClienteChat extends JFrame
{
	private Image icono;
	private InterfazCliente cliente1;
	private int ventanas;
	
	public MarcoClienteChat(String nombre)
	{   //CONSTRUCTOR PRIMERO DE RECEPCIÓN DEL NOMBRE USUARIO

        this.icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA CLIENTE");
		setBounds(300,100,350,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.cliente1= new InterfazCliente(nombre);
        add(cliente1);
        setVisible(true);
        
        estableceConexion();   //Que se ejecute el segundo tunel de conexion mientras se abre el cliente
	}
	void estableceConexion()
	{
		try {
			//Estableciendo la conexion
			Socket tunelComunicacion= new Socket("192.168.1.136",9999);

			//Paquete de datos para enviar IP del seleccionador
			PaqueteMensaje datosHost= new PaqueteMensaje();

			//Incluye la palabra ONLINE para que el servidor sepa que es un cliente que se conecta y no un mensaje de otro cliente
			datosHost.setMensaje("online");
			
			//Establecimiento de la transmisión de datos
			ObjectOutputStream flujoSalidaPaquete= new ObjectOutputStream(tunelComunicacion.getOutputStream());
			flujoSalidaPaquete.writeObject(datosHost);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private JLabel Cajanick;
	
	private JLabel TituloIPpersonaConec; 
	private JComboBox<String> CajaIPpersonaConec;
	private String nombreUsuario="";
	
	public InterfazCliente(String nombre)
	{
		//SE LE INDICA EL NOMBRE QUE SE LE HABIA ASIGNADO ANTERIORMENTE EN EL JOPTIONPANE
		this.nombreUsuario=nombre;
		setLayout(null);  //Para que respeten el setBounds
		this.Titulonick= new JLabel("NICK DE CONEXION:");
		this.Cajanick= new JLabel(this.nombreUsuario);
		
		this.TituloIPpersonaConec= new JLabel("CONECTAR CON:");
		this.CajaIPpersonaConec= new JComboBox();
		
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
		
		this.Titulonick.setBounds(50,350,200,20);
		this.Cajanick.setBounds(50,370,200,20);
		
		this.TituloIPpersonaConec.setBounds(50,400,200,20);
		this.CajaIPpersonaConec.setBounds(50,420,200,20);
		
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
			mensaje.setIP(CajaIPpersonaConec.getSelectedItem().toString());   //Devuelve el primer elemento pasado a string
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
				
				if(paqueteRecibido.getMensaje().equals("online"))
				{
					//SE ACABA DE CONECTAR EL CLIENTE Y DEBE RELLENAR EL COMBOBOX POR CADA CONEXION QUE SE HAYA UNIDO
					//NO USAR ADDITEM PORQUE LO RELLENARA EN HORIZONTAL
					ArrayList<String>IpsJCombo= new ArrayList<String>();
					ArrayList<String>NickJCombo= new ArrayList<String>();
					IpsJCombo=paqueteRecibido.getListaIps();
					NickJCombo=paqueteRecibido.getListaNicks();
					
					HashMap<String,String> NickIP= new HashMap<String,String>();
					NickIP.put(nombreUsuario, nombreUsuario);
					//Antes de proceder con el rellenado hay un detalle que tener en cuenta
					//Si no se borra lo que ya tiene el JComboBox, el for añadira lo que ya tenía mas el nuevo ArrayList 
					//Y se verán IPS duplicadas para el primer cliente y no para el segundo, en un primer ciclo
					// Triplicadas en un primer cliente, duplicadas en un segundo cliente y no para el tercero, en un segundo ciclo
					//Y así sucesivamente, luego se guardara siempre el ultimo ArrayList generado.
					
					CajaIPpersonaConec.removeAllItems();  //Elimina todos los elementos del JComboBox completo
						for(String IPS: IpsJCombo)
						{
							//Rellena el JComboBox por cada usuario que se conecte
							CajaIPpersonaConec.addItem(IPS);
						}
						for(String NICKS: NickJCombo)
						{
							//Rellena el JComboBox por cada usuario que se conecte
							System.out.println(NICKS);
						}
				}
				else
				{
					//EN CASO DE QUE NO SE ACABE DE CONECTAR SINO QUE ES MERA CONVERSACIÓN CON OTRO CLIENTE
					cajaTextoRespuesta.append("\n"+paqueteRecibido.getNick()+"\n"+paqueteRecibido.getMensaje());
				}
				
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
	private ArrayList<String> listaIps;
	private ArrayList<String> listaNicks;
	
	public PaqueteMensaje()
	{
		
	}
	
	public PaqueteMensaje(String nick,String IP,String mensaje)
	{
		this.nick=nick;
		this.IP=IP;
		this.mensaje=mensaje;
		
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
}