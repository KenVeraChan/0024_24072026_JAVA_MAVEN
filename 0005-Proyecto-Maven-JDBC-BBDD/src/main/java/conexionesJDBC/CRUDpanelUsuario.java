package conexionesJDBC;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jdesktop.swingx.JXDatePicker;

public class CRUDpanelUsuario {

}

//AREA SE INSERCCIONES
class MarcoInsertar extends JFrame
{
	public MarcoInsertar()
	{
		setBounds(450,50,500,630);
		setTitle("AREA SE INSERCCIÓN");
		setIconImage(new ImageIcon("ficherosUtilizados/icono.png").getImage());  //CAMBIA EL ICONO DE LA APLICACION

		setResizable(false);
		PanelInsertar lamina1= new PanelInsertar("ficherosUtilizados/paisaje.jpg",30);
		add(lamina1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
class PanelInsertar extends JPanel implements ActionListener
{
    private Image imagen;
    private float alfaImagen = 1.0f; // opaco por defecto
 
	//JLABELS DE LAS ENTRADAS DE DATOS
	
	private JLabel codigo;
	private JLabel seccion;
	private JLabel nombre;
	private JLabel descripcion;
	private JLabel precio;
	private JLabel fecha;
	private JLabel importado;
	private JLabel pais;
	
	//JTEXTAREAS DE LAS ENTRADAS DE DATOS
		
	private JTextArea cajacodigo;
	private JComboBox<String> cajaseccion;
	private JTextArea cajanombre;
	private JTextArea cajadescripcion;
	private JComboBox<String> cajaprecio;
	private JComboBox<String> cajafecha;
	private JComboBox<String> cajaimportado;
	private JComboBox<String> cajapais;
		
	//DECLARACION DE BOTONES Y ACCIONAMIENTOS
	private JButton aceptar;
	private JButton cancelar;
		
	//DECLARACION DEL ACCIONAMIENTO FECHA
	private JXDatePicker datePicker = new JXDatePicker();
    private Date calendario;
    
	public PanelInsertar(String ruta, int transparencia)
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
		//ESTETICA DE CAJAS-TITULOS-DESPLEGABLES-FECHAS
			setLayout(null);  //Para que respeten el setBounds
			
		//JLABELS DE LAS ENTRADAS DE DATOS
			
			this.codigo = new JLabel("CODIGO");
			this.seccion= new JLabel("SECCIÓN");
			this.nombre = new JLabel("NOMBRE");
			this.descripcion= new JLabel("DESCRIPCIÓN");
			this.precio = new JLabel("PRECIO");
			this.fecha= new JLabel("FECHA");
			this.importado = new JLabel("IMPORTADO");
			this.pais= new JLabel("PAIS ORIGEN");
			
		//JTEXTAREAS DE LAS ENTRADAS DE DATOS
			
			this.cajacodigo = new JTextArea();
			this.cajaseccion= new JComboBox<String>();
			this.cajanombre = new JTextArea();
			this.cajadescripcion = new JTextArea();
			this.cajaprecio = new JComboBox<String>();
			this.cajafecha = new JComboBox<String>();
			this.cajaimportado = new JComboBox<String>();
			this.cajapais = new JComboBox<String>();
			
		//DECLARACION DE BOTONES Y ACCIONAMIENTOS
			this.aceptar= new JButton("ACEPTAR");
			this.cancelar= new JButton("SALIR");
			
		//DECLARACION DEL ACCIONAMIENTO FECHA
			this.datePicker = new JXDatePicker();
			
		//ESTETICA DE BOTON DE FECHA SWING
			this.datePicker.setFormats("dd/MM/yyyy");
			datePicker.addActionListener(e -> {
			    this.calendario = this.datePicker.getDate();  //Si no se ha elegido fecha se pone Hoy
			});

		//ESTETICA DE CAJAS-TITULOS-DESPLEGABLES-FECHAS
			setLayout(null);  //Para que respeten el setBounds			
			
			this.codigo.setBounds(20, 30, 450, 30);
			this.cajacodigo.setBounds(20, 60, 450, 30);
			
			this.seccion.setBounds(20, 90, 450, 30);
			this.cajaseccion.setBounds(20, 120, 450, 30);
			
			this.nombre.setBounds(20, 150, 450, 30);
			this.cajanombre.setBounds(20, 180, 450, 30);
			
			this.descripcion.setBounds(20, 210, 450, 30);
			this.cajadescripcion.setBounds(20, 240, 450, 30);		
			
			this.precio.setBounds(20, 270, 450, 30);
			this.cajaprecio.setBounds(20, 300, 450, 30);
			
			this.fecha.setBounds(20, 330, 450, 30);
			this.datePicker.setBounds(20, 360, 450, 30);
			
			this.importado.setBounds(20, 390, 450, 30);
			this.cajaimportado.setBounds(20,420,450,30);
			
			this.pais.setBounds(20, 450, 450, 30);
			this.cajapais.setBounds(20, 480, 450, 30);
			
			this.aceptar.setBounds(120, 530, 100, 30);
			this.cancelar.setBounds(250,530,100,30);
		
		//COLOCACION DE COMPONENTES
			add(this.codigo);
			add(this.cajacodigo);
			add(this.seccion);
			add(this.cajaseccion);
			add(this.nombre);
			add(this.cajanombre);
			add(this.descripcion);
			add(this.cajadescripcion);
			add(this.precio);
			add(this.cajaprecio);
			add(this.fecha);
			add(this.cajafecha);
			add(this.importado);
			add(this.cajaimportado);
			add(this.pais);
			add(this.cajapais);
			add(this.aceptar);
			add(this.cancelar);
			add(this.fecha);
			add(this.datePicker);
			
		//ESTETICA DEL BOTON CANCELAR OPERACION
			this.cancelar.addActionListener(this);
			this.aceptar.addActionListener(this);
	}
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
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == aceptar) {
			try {
				//SE ADICIONA EL PETICIONADOR AL USUARIO
				String producto= JOptionPane.showInputDialog("INTRODUZCA EL ARTÍCULO QUE QUIERE ALMACENAR");
				
				//1 - CREAR CONEXION
				//En el caso de MYSQL
				Connection conector= DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebasdef","root","1234");
				
				//2 - CREAR EL STATENMENT
				Statement myst = conector.createStatement();
				
				//3 - CREAR INSTRUCCIÓN SQL
				String inSQL="INSERT INTO productos(CODIGOARTICULO,NOMBREARTICULO,PRECIO) VALUES ('AR45','"+producto+"',50)";

				//4 - EJECUTAR SQL
				myst.executeUpdate(inSQL);
				
				//5 - CERRAR LA CONEXION
				conector.close(); 
				
				JOptionPane.showMessageDialog(null, "INFORMACIÓN ACTUALIZADA");
			} catch (SQLException error) {
				// TODO Auto-generated catch block
				error.printStackTrace();
			}  
        }

        if (src == cancelar) {
            JOptionPane.showMessageDialog(null, "Ha decidido salir, Hasta luego");
            System.exit(0);
        }
    }
}