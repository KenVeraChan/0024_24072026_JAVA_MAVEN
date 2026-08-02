package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdesktop.swingx.JXDatePicker;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
		setBounds(450,50,500,630);
		setTitle("Generador de Exámenes - En línea Ken_R.W.R.");
		setIconImage(new ImageIcon("ficherosUtilizados/icono.png").getImage());  //CAMBIA EL ICONO DE LA APLICACION

		setResizable(false);
		laminaDispuesta lamina1= new laminaDispuesta(recursos,"ficherosUtilizados/paisaje.jpg",30);
		add(lamina1);
	}
}

class laminaDispuesta extends JPanel implements ActionListener
{
	//DECLARACION DE COLECCIONES DE TIPO ARRAYLIST
	private List<String>baseDatosAlumnos= new ArrayList<>();
	private List<String>baseDatosCursos= new ArrayList<>();
	private List<String>baseDatosAsignaturas= new ArrayList<>();
	private List<String>baseDatosTemarios= new ArrayList<>();
	private List <String> asignaturasFiltradas= new ArrayList<>();  //ArrayList para actualizar en el combo
	private Map<String, String> materias = new HashMap<>();         //Mapeo de asignaturas
	private Map<String, String> niveles = new HashMap<>();			//Mapeo de cursos
	
	//DECLARACION DE DESPLEGABLES
	private JComboBox<String> cajaNombre= new JComboBox<String>();
	private JComboBox<String> cajaCursos= new JComboBox<String>();
	private JComboBox<String> cajaAsignaturas= new JComboBox<String>();
	private JComboBox<String> cajaTemario1= new JComboBox<String>();
	private JComboBox<String> cajaTemario2= new JComboBox<String>();
	private JComboBox<String> cajaTemario3= new JComboBox<String>();
	private JComboBox<String> cajaTemario4= new JComboBox<String>();
	private JComboBox<String> cajaTemario5= new JComboBox<String>();
	
	//DECLARACION DE TITULOS DE BOTONES O DESPLEGABLES
	private JLabel nombre= new JLabel("NOMBRE y APELLIDOS: ");
	private JLabel curso= new JLabel("CURSO: ");
	private JLabel fecha= new JLabel("FECHA: ");
	private JLabel asignatura= new JLabel("ASIGNATURA: ");
	private JLabel temario1= new JLabel("TEMARIO PREGUNTA 1: ");
	private JLabel temario2= new JLabel("TEMARIO PREGUNTA 2: ");
	private JLabel temario3= new JLabel("TEMARIO PREGUNTA 3: ");
	private JLabel temario4= new JLabel("TEMARIO PREGUNTA 4: ");
	private JLabel temario5= new JLabel("TEMARIO PREGUNTA 5: ");
	private JLabel horarioExamen= new JLabel("HORA: ");
	
	//DECLARACION DE BOTONES Y ACCIONAMIENTOS
	private JButton aceptar= new JButton("ACEPTAR");
	private JButton cancelar= new JButton("CANCELAR");
	
	//DECLARACION DEL ACCIONAMIENTO FECHA
	private JXDatePicker datePicker = new JXDatePicker();
    private Date calendario;
    
    //DECLARACION DEL ACCIONAMIENTO HORA
    private String[] horas = new String[48];
    private int puntero=0;
    JComboBox<String> horario;
   
	//Adapatabilidad de los formatos
	private String cursoSeleccionado="";
	private String asignaturaSeleccionada="";
	private String horarioSeleccionado="";
	private String nivel="";
	private String materia="";
    private Image imagen;
    private float alfaImagen = 1.0f; // opaco por defecto
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alfaImagen));
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }
    }
	public laminaDispuesta(CargaRecursos basedatos,String ruta,int transparencia)
	{
    /////// TRATAMIENTO DE FONDO LAMINA /////////////////////
        try {
            if (ruta.startsWith("http")) {
                // URL remota
                URL url = new URL(ruta);
                this.imagen = ImageIO.read(url);
            }
            else if (ruta.startsWith("file:/")) {
                // URL local absoluta
                URL url = new URL(ruta);
                this.imagen = ImageIO.read(url);
            }
            else {
                // Ruta local normal (tu caso)
                File archivo = new File(ruta);
                this.imagen = ImageIO.read(archivo);
            }
            // transparencia de 0 a 100 → alpha de 0.0 a 1.0
            this.alfaImagen = Math.max(0, Math.min(100, transparencia)) / 100f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(null); // si quieres colocar componentes con setBounds()		

   ////////////////////////////////////////////////////////
 
   //ASIGNACION DE MAPEOS EN ASIGNATURAS (MATERIAS) Y CURSOS (NIVELES)////
		this.materias.put("Matemáticas", "M");
		this.materias.put("Física y Química", "FYQ");
		this.materias.put("Física", "Física");
		this.materias.put("Química", "Química");
		this.materias.put("Tecnología", "Tecnología");
		this.materias.put("Dibujo Técnico", "Dibujo Tec");
		this.materias.put("Economía", "Economía");
		this.materias.put("Programación", "Programación");
		this.materias.put("Electrotecnia", "Electrotecnia");
		
		this.niveles.put("1 ESO", "1 ESO");
		this.niveles.put("2 ESO", "2 ESO");
		this.niveles.put("3 ESO", "3 ESO");
		this.niveles.put("4 ESO", "4 ESO");
		this.niveles.put("1 BACHILLERATO", "1 BTO");
		this.niveles.put("2 BACHILLERATO", "2 BTO");
		
		setBackground(new Color(131,250,120));

		basedatos.generadorFichero(4);   //Rellena los ArrayList que luego se usaran
		
		this.baseDatosAlumnos=basedatos.getAlumnos();   //Recogiedno lo leido del EXCEL en ALUMNOS
		Collections.sort(this.baseDatosAlumnos, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO

		this.baseDatosCursos= basedatos.getCursos();    //Recogiendo lo leido del EXCEL en CURSOS
		Collections.sort(this.baseDatosCursos, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO
		
		this.baseDatosAsignaturas= basedatos.getAsignaturas();    //Recogiendo lo leido del EXCEL en ASIGNATURAS
		Collections.sort(this.baseDatosAsignaturas, String.CASE_INSENSITIVE_ORDER);   //SE ORDENA EN ORDEN ALFABÉTICO

		//Lista COMPLETA de temarios leida del EXCEL. NO se sobreescribe: es la fuente que se filtra.
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

		//Rellenando el BOTON DE HORAS SWING
	    for(int h = 0; h<24;h++) 
	    {
	        this.horas[puntero++] = String.format("%02d:00", h);
	        this.horas[puntero++] = String.format("%02d:30", h);
	    }
	    this.horario = new JComboBox<>(horas);   //Se introducen todas las horas generadas en el combobox de horas

		//Guardamos la seleccion inicial (primer elemento que muestra cada combo)
		this.cursoSeleccionado = (String) this.cajaCursos.getSelectedItem();
		this.asignaturaSeleccionada = (String) this.cajaAsignaturas.getSelectedItem();
		
		//UN CAMBIO EN ALGUNO DE AMBOS, MODIFICARÁ EL MENU DE TEMARIOS MOSTRADOS
		this.cajaCursos.addItemListener(e -> {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		        this.cursoSeleccionado = (String) this.cajaCursos.getSelectedItem();
		        actualizaMenu();
		    }
		});
		this.cajaAsignaturas.addItemListener(e -> {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		        this.asignaturaSeleccionada = (String) this.cajaAsignaturas.getSelectedItem();
		        actualizaMenu();
		    }
		});
		
		//DETECTOR EN EL CAMBIO DE ELECCION DEL DESPLEGABLE DE LAS HORAS
		this.horario.addItemListener(e -> {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		        this.horarioSeleccionado = (String) this.horario.getSelectedItem();
		        actualizaMenu();
		    }
		});
		
		//Primer volcado de temarios segun la seleccion inicial de curso y asignatura
		actualizaMenu();
		
		//ESTETICA DE BOTON DE FECHA SWING
		this.datePicker.setFormats("dd/MM/yyyy");
		datePicker.addActionListener(e -> {
		    this.calendario = datePicker.getDate();
		});
		
		//ESTETICA DE BOTONES Y ACCIONAMIENTOS
		this.aceptar.addActionListener(e->{
			basedatos.generadorFichero(5);   //CREA EL EXCEL NECESARIO
			gestionExamenExcel preparacion= new gestionExamenExcel();
			preparacion.preparacionExamenExcel(
					(String)this.cajaNombre.getSelectedItem(),
					this.cursoSeleccionado,
					this.asignaturaSeleccionada,
					(String)this.cajaTemario1.getSelectedItem(),
					(String)this.cajaTemario2.getSelectedItem(),
					(String)this.cajaTemario3.getSelectedItem(),
					(String)this.cajaTemario4.getSelectedItem(),
					(String)this.cajaTemario5.getSelectedItem(),
					this.calendario,
					this.horarioSeleccionado);
		});
		this.cancelar.addActionListener(e->{
			System.exit(0);  //Sale del programa y termina
		});
		
		//ESTETICA DE CAJAS-TITULOS-DESPLEGABLES-FECHAS
		setLayout(null);  //Para que respeten el setBounds
		this.nombre.setBounds(20, 30, 450, 30);
		this.cajaNombre.setBounds(20, 60, 450, 30);
		
		this.curso.setBounds(20, 90, 450, 30);
		this.cajaCursos.setBounds(20, 120, 150, 30);
		
		this.fecha.setBounds(200, 90, 450, 30);
		this.datePicker.setBounds(200, 120, 120, 30);
		
		this.horarioExamen.setBounds(350, 90, 100, 30);
		this.horario.setBounds(350, 120, 80, 30);		
		
		this.fecha.setBounds(200, 90, 450, 30);
		this.datePicker.setBounds(200, 120, 120, 30);
		
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
		
		this.aceptar.setBounds(20, 530, 100, 30);
		this.cancelar.setBounds(150,530,100,30);
		
		//COLOCACION DE COMPONENTES
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
		add(this.aceptar);
		add(this.cancelar);
		add(this.fecha);
		add(this.datePicker);
		add(this.horario);
		add(this.horarioExamen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
	public void actualizaMenu()
	{
		//Traducimos lo elegido en los combos al codigo que usa el fichero EXCEL
		this.nivel = this.niveles.get(this.cursoSeleccionado);      //p.ej. "1 BACHILLERATO" -> "1 BTO"
		this.materia = this.materias.get(this.asignaturaSeleccionada); //p.ej. "Matemáticas" -> "M"
		
		//Filtramos SIEMPRE sobre la lista COMPLETA de temarios (baseDatosTemarios)
		this.asignaturasFiltradas = new ArrayList<>();   //se reinicia en cada llamada para no acumular
		if (this.nivel != null && this.materia != null) {
			for (String temario : this.baseDatosTemarios) {
				//Formato de ancho fijo: [0-4]=nivel, [7-17]=codigo materia (relleno con '-'), [18]=espacio, [19+]=tema
				if (temario == null || temario.equals("NADA") || temario.length() < 19) {
					continue;
				}
				String codigoMateria = temario.substring(7, 18).replace("-", "").trim();
				if (temario.startsWith(this.nivel) && codigoMateria.equals(this.materia)) {
					this.asignaturasFiltradas.add(temario);
				}
			}
		}
		//Volcamos el resultado ya filtrado en los cinco JComboBox de temario
		List<JComboBox<String>> cajasTemario = Arrays.asList(
				this.cajaTemario1, this.cajaTemario2, this.cajaTemario3,
				this.cajaTemario4, this.cajaTemario5);
		for (JComboBox<String> caja : cajasTemario) {
			caja.removeAllItems();                       //limpia lo mostrado anteriormente
			for (String temario : this.asignaturasFiltradas) {
				caja.addItem(temario);                   //rellena con las asignaturas/temas ya filtrados
			}
		}
	}
}
