package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		setBounds(300,50,500,600);
		setTitle("Menu de opciones");
		setResizable(false);
		laminaDispuesta lamina1= new laminaDispuesta(recursos);
		add(lamina1);
	}
}

class laminaDispuesta extends JPanel implements ActionListener
{
	private List<String>baseDatosAlumnos= new ArrayList<>();
	private List<String>baseDatosCursos= new ArrayList<>();
	private List<String>baseDatosAsignaturas= new ArrayList<>();
	private List<String>baseDatosTemarios= new ArrayList<>();
	private JComboBox<String> cajaNombre= new JComboBox<String>();
	private JComboBox<String> cajaCursos= new JComboBox<String>();
	private JComboBox<String> cajaAsignaturas= new JComboBox<String>();
	private JComboBox<String> cajaTemario1= new JComboBox<String>();
	private JComboBox<String> cajaTemario2= new JComboBox<String>();
	private JComboBox<String> cajaTemario3= new JComboBox<String>();
	private JComboBox<String> cajaTemario4= new JComboBox<String>();
	private JComboBox<String> cajaTemario5= new JComboBox<String>();
	
	private JLabel nombre= new JLabel("NOMBRE y APELLIDOS: ");
	private JLabel curso= new JLabel("CURSO: ");
	private JLabel asignatura= new JLabel("ASIGNATURA: ");
	private JLabel temario1= new JLabel("TEMARIO PREGUNTA 1: ");
	private JLabel temario2= new JLabel("TEMARIO PREGUNTA 2: ");
	private JLabel temario3= new JLabel("TEMARIO PREGUNTA 3: ");
	private JLabel temario4= new JLabel("TEMARIO PREGUNTA 4: ");
	private JLabel temario5= new JLabel("TEMARIO PREGUNTA 5: ");
	
	private String cursoSeleccionado="";
	private String asignaturaSeleccionada="";
	
	public laminaDispuesta(CargaRecursos basedatos)
	{
		setBackground(new Color(131,250,120));
		
		//UN CAMBIO EN ALGUNO DE AMBOS, MODIFICARÁ EL MENU DE ASIGNATURAS MOSTRADAS
		this.cajaCursos.addItemListener(e -> {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		        this.cursoSeleccionado = (String) cajaCursos.getSelectedItem();
		        System.out.println("Curso Seleccionado: " + this.cursoSeleccionado);
		    }
		});
		this.cajaAsignaturas.addItemListener(e -> {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		        this.asignaturaSeleccionada = (String) cajaAsignaturas.getSelectedItem();
		        System.out.println("Asignatura Seleccionada: " + this.asignaturaSeleccionada);
		    }
		});
		
		basedatos.generadorFichero(4);
		
		this.baseDatosAlumnos=basedatos.getAlumnos();   //Recogiedno lo leido del EXCEL en ALUMNOS
		Collections.sort(this.baseDatosAlumnos, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO

		this.baseDatosCursos= basedatos.getCursos();    //Recogiendo lo leido del EXCEL en CURSOS
		Collections.sort(this.baseDatosAlumnos, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO
		
		this.baseDatosAsignaturas= basedatos.getAsignaturas();    //Recogiendo lo leido del EXCEL en ASIGNATURAS
		Collections.sort(this.baseDatosAsignaturas, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO

		this.baseDatosTemarios= basedatos.getTemarios();    //Recogiendo lo leido del EXCEL en TEMARIOS
		Collections.sort(this.baseDatosTemarios, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO
		
		for(String dato: this.baseDatosAlumnos)   //Rellenando el COMBOBOX de ALUMNOS
		{
			this.cajaNombre.addItem(dato);   //Se rellena el COMBOOX
		}	
		for(String dato: this.baseDatosCursos)   //Rellenando el COMBOBOX de CURSOS
		{
			this.cajaCursos.addItem(dato);   //Se rellena el COMBOOX
		}	
		for(String dato: this.baseDatosAsignaturas)   //Rellenando el COMBOBOX de ASIGNATURAS
		{
			this.cajaAsignaturas.addItem(dato);   //Se rellena el COMBOOX
		}	
		this.baseDatosTemarios=actualizaMenu();
			for(String dato: this.baseDatosTemarios)   //Rellenando el COMBOBOX de TEMARIOS
			{
				this.cajaTemario1.addItem(dato);   //Se rellena el COMBOOX
				this.cajaTemario2.addItem(dato);   //Se rellena el COMBOOX
				this.cajaTemario3.addItem(dato);   //Se rellena el COMBOOX
				this.cajaTemario4.addItem(dato);   //Se rellena el COMBOOX
				this.cajaTemario5.addItem(dato);   //Se rellena el COMBOOX
			}
		
		setLayout(null);  //Para que respeten el setBounds
		this.nombre.setBounds(20, 30, 450, 30);
		this.cajaNombre.setBounds(20, 60, 450, 30);
		this.curso.setBounds(20, 90, 450, 30);
		this.cajaCursos.setBounds(20, 120, 450, 30);
		this.asignatura.setBounds(20, 150, 450, 30);
		this.cajaAsignaturas.setBounds(20, 180, 450, 30);
		
		this.temario1.setBounds(20, 210, 450, 30);
		this.cajaTemario1.setBounds(20, 240, 450, 30);
		
		this.temario2.setBounds(20, 270, 450, 30);
		this.cajaTemario2.setBounds(20, 300, 450, 30);
		
		this.temario3.setBounds(20, 330, 450, 30);
		this.cajaTemario3.setBounds(20, 360, 450, 30);
		
		this.temario4.setBounds(20, 390, 450, 30);
		this.cajaTemario4.setBounds(20, 420, 450, 30);
		
		this.temario5.setBounds(20, 450, 450, 30);
		this.cajaTemario5.setBounds(20, 480, 450, 30);
		add(this.nombre);
		add(this.cajaNombre);
		add(this.curso);
		add(this.cajaCursos);
		add(this.asignatura);
		add(this.cajaAsignaturas);
		add(this.temario1);
		add(this.cajaTemario1);
		add(this.temario2);
		add(this.cajaTemario2);
		add(this.temario3);
		add(this.cajaTemario3);
		add(this.temario4);
		add(this.cajaTemario4);
		add(this.temario5);
		add(this.cajaTemario5);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	public List<String> actualizaMenu()
	{
	   //Adapatabilidad de los formatos
		List <String> asignaturasFiltradas= new ArrayList<>();
		Map<String, String> codigos = new HashMap<>();
		codigos.put("Matemáticas", "M");
		codigos.put("Física y Química", "FYQ");
		codigos.put("Física", "Física");
		codigos.put("Química", "Química");
		codigos.put("Tecnología", "Tecnología");
		codigos.put("Dibujo Técnico", "Dibujo");
		codigos.put("Economía", "Economía");
		codigos.put("Programación", "Programación");
		codigos.put("Electrotecnia", "Electrotecnia");

		String codigo = codigos.get(this.asignaturaSeleccionada);
 
		for (String asignatura : this.baseDatosTemarios) {
		    if (asignatura.startsWith(this.cursoSeleccionado)
		        && asignatura.substring(7).replace("-", " ").substring(0,asignatura.substring(7).replace("-", " ").indexOf(" ")).equals(codigo)) {
		        asignaturasFiltradas.add(asignatura);
		    }
		}
	   return asignaturasFiltradas;
	}
}