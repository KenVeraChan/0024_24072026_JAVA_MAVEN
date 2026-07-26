package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfazUsuario {
	public InterfazUsuario()
	{
		laminaFondo fondo= new laminaFondo();
		fondo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fondo.setVisible(true);
	}
}

class laminaFondo extends JFrame
{
	private CargaRecursos recursos = new CargaRecursos();
	
	public laminaFondo()
	{
		setBounds(400,300,700,700);
		setTitle("Menu de opciones");
		setResizable(false);
		laminaDispuesta lamina1= new laminaDispuesta(recursos);
		add(lamina1);
	}
}

class laminaDispuesta extends JPanel
{
	private List<String>baseDatos= new ArrayList<>();

	public void setBaseDatos(List<String> baseDatos)
	{
		this.baseDatos=baseDatos;
	}
	
	public laminaDispuesta(CargaRecursos basedatos)
	{
		JLabel nombre= new JLabel("NOMBRE y APELLIDOS: ");
		JLabel telefono= new JLabel("TELEFONO: ");
		JTextField cajaTelefono= new JTextField();
		JComboBox<String> cajaNombre= new JComboBox<String>();
		
		basedatos.generadorFichero(4);
		this.baseDatos=basedatos.getDatos();
		
		for(String dato: this.baseDatos)
		{
			cajaNombre.addItem(dato);   //Se rellena el COMBOOX
		}	
		setLayout(null);  //Para que respeten el setBounds
		nombre.setBounds(20, 30, 450, 30);
		cajaNombre.setBounds(20, 60, 450, 30);
		telefono.setBounds(20, 90, 450, 30);
		cajaTelefono.setBounds(20, 120, 450, 30);
		add(nombre);
		add(cajaNombre);
		add(telefono);
		add(cajaTelefono);
	}
}