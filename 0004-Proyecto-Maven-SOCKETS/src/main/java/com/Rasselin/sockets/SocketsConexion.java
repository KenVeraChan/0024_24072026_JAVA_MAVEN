package com.Rasselin.sockets;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SocketsConexion {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Cliente ventanaCliente= new Cliente();
    }
}


class Cliente extends JFrame
{
	public Cliente()
	{
        Image icono = new ImageIcon("ficherosUsados/icono.png").getImage();
        setIconImage(icono);        
        setTitle("VENTANA CLIENTE");
		setBounds(200,200,300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        InterfazCliente cliente1= new InterfazCliente();
        add(cliente1);
        
        setVisible(true);
	}
}

class InterfazCliente extends JPanel
{
	public InterfazCliente()
	{
		setLayout(null);  //Para que respeten el setBounds
		JLabel cajaEnvio= new JLabel("TEXTO PARA ENVIAR");
		JTextField cajaTexto= new JTextField();
		cajaEnvio.setBounds(50, 50, 120, 20);
		cajaTexto.setBounds(50, 80, 120, 20);
		add(cajaEnvio);
		add(cajaTexto);
	}

}