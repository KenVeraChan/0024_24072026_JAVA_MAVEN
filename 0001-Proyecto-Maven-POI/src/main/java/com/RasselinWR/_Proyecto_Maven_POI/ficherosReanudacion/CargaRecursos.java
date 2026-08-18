package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.PageMargin;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXDatePicker;

//GESTOR DE FICHEROS EN EXCEL
public class CargaRecursos {
	private String nombre="";
	private int edad=0;
	private String profesion="";
	private int seleccionador=0;
	private List<String> alumnos= new ArrayList<>();
	private List<String> cursos= new ArrayList<>();
	private List<String> asignaturas = new ArrayList<>();
	private List<String> temarios = new ArrayList<>();
	private String rutaGenerados="ficherosGenerados/examen.xlsx";
	private String rutaUtilizados="ficherosUtilizados/AlumnosArces3Formacion.xlsm";
	
	public CargaRecursos(){}
	public void cargaMenu()
	{
		//Carga el menu antes de seguir
		InterfazUsuario nuevoFondo= new InterfazUsuario();
	}
	public List<String> getAlumnos()
	{
		return this.alumnos;
	}
	public List<String> getCursos()
	{
		return this.cursos;
	}
	public List<String> getAsignaturas()
	{
		return this.asignaturas;
	}
	public List<String> getTemarios()
	{
		return this.temarios;
	}
	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	public String getNombre()
	{
		return this.nombre;
	}
	public void setEdad(int edad)
	{
		this.edad=edad;
	}
	public int getEdad()
	{
		return this.edad;
	}
	public void setProfesion(String profesion)
	{
		this.profesion=profesion;
	}
	public String getProfesion()
	{
		return this.profesion;
	}
	public void setSeleccionador(int seleccionador)
	{
		this.seleccionador=seleccionador;
	}
	public int getSeleccionador()
	{
		return this.seleccionador;
	}
	public void generadorFichero(int selector)
	{
		File file = new File(this.rutaGenerados);   //getResourceAsStream() para lectura solo de ficheros

		switch(selector)
		{
			case 1: //ESCRIBIR EN FICHERO EXCEL
			{		            
				try {
						InputStream ruta = new FileInputStream(file);
			            Workbook libro = new XSSFWorkbook(ruta);    //Asignacion de ruta
			            Sheet hoja = libro.createSheet("Datos");
			            
			            Row fila = hoja.createRow(0);        // fila 0
			            Cell celda = fila.createCell(0);     // columna 0
			            celda.setCellValue(123.45);          // n�mero en la celda
		            	
		            	FileOutputStream archivo= new FileOutputStream(file);
			            libro.write(archivo);
			            archivo.close();
			            libro.close();
			            System.out.println("Excel editado correctamente");	            
		        } catch (Exception e) {
		            e.printStackTrace();
		            System.out.println("El error del caso 1 ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
		            System.out.println("Se debera crear el fichero de EXCEL previamente");
		        }
				break;
			}
			case 2: //LEER DE UN FICHERO DE EXCEL DE PRUEBA
			{
				//DEFINIMOS LOS PARAMETROS DEL FICHERO
		        List<String> datosColumna = new ArrayList<>();
		        try (
	        	 FileInputStream fis = new FileInputStream(file);
	             Workbook workbook = new XSSFWorkbook(fis)) {

		            Sheet hoja = workbook.getSheetAt(1); // primera hoja del Excel

		            for (Row fila : hoja) {
		                Cell celda = fila.getCell(0); // primera columna (índice 0)

		                if (celda != null) {    //Si es distinto a NULL podra guardar el dato
		                    datosColumna.add(celda.toString());   //Adiciona el dato
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		            System.out.println("El error del caso 2 ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
		        }

		        // MOSTRAR RESULTADOS
		        System.out.println("Datos leídos de la primera columna:");
		        for (String dato : datosColumna) {
		            System.out.println(dato);
		        }
				break;
			}
			case 3: //CREA FICHERO DE TIPO TXT
			{
				File fileTXT = new File("ficherosGenerados/Facturas.txt");    //getResourceAsStream() para lectura solo de ficheros
				if(!fileTXT.exists())
				{
					try {
						fileTXT.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
			            e.printStackTrace();
			            System.out.println("El error de del caso 3 creacion del fichero TXT ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
					}
					System.out.println("Fichero creado correctamente");
				}
				else 
				{
					System.out.println("Fichero ya estaba creado");
				}
				break;
			}
			case 4: //LEER DE UN FICHERO DE EXCEL DEL MOTIVADO PROYECTO
			{
				Set<String> datosColumnaAlumnos = new HashSet<>();
		        Set<String> datosColumnaCursos = new HashSet<>();
		        Set<String> datosColumnaAsignaturas= new HashSet<>(); //COLUMNA 51 DEL FICHERO EXCEL DE LA HOJA "ALUMNOS INFORMES" DESDE LA FILA 5
		        Set<String> datosColumnaTemarios= new HashSet<>(); //COLUMNA 14 DEL FICHERO EXCEL DE LA HOJA "HOJA EXAMEN" DESDE LA FILA 2

		        try (
		        		
		        	 FileInputStream fis = new FileInputStream(this.rutaUtilizados);
		             Workbook workbook = new XSSFWorkbook(fis)) {

		            Sheet hoja = workbook.getSheetAt(3); // cuarta hoja del Excel
		            for (int i = 3; i <= hoja.getLastRowNum(); i++) 
		            {   // empieza en la fila 4 empezando a contar desde 0
		                Row fila = hoja.getRow(i);

		                if (fila==null) continue;   // si la fila está vacía, saltamos
		                Cell celdaAsignatura= fila.getCell(50);  //Quincuagésimaprimera columna (indice50) la de las asignaturas
		                Cell celdaTemario = fila.getCell(52); // Quincuagésimatercera columna (índice 52) la de los temarios
		                Cell celdaCurso = fila.getCell(2);  // tercera columna (indice 2) la de los cursos
		                Cell celdaAlumno = fila.getCell(1); // segunda columna (índice 1) la de los alumnos

		                if(celdaAlumno != null){datosColumnaAlumnos.add(celdaAlumno.toString());}
		                if(celdaCurso !=null){datosColumnaCursos.add(celdaCurso.toString());}
		                if(celdaAsignatura!=null){datosColumnaAsignaturas.add(celdaAsignatura.toString());}
		                if(celdaTemario!=null){datosColumnaTemarios.add(celdaTemario.toString());}
		            }
			        this.alumnos=new ArrayList<>(datosColumnaAlumnos);  //Se actualiza aqui el ArrayList de alumnos convertido de un Set para un List 
			        this.cursos = new ArrayList<>(datosColumnaCursos); //Se actualiza aqui el ArrayList convertido de un Set para un List
			        this.asignaturas= new ArrayList<>(datosColumnaAsignaturas); //Se actualiza aqui el ArrayList convertido de un Set para un List
			        this.temarios= new ArrayList<>(datosColumnaTemarios); //Se actualiza aqui el ArrayList convertido de un Set para un List
		        } catch (IOException e) {
		            e.printStackTrace();
		            System.out.println("El error del caso 4 ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
		        }
				break;
			}
			case 5:
			{
		        // 1. Crear libro nuevo
		        Workbook libro = new XSSFWorkbook();   // .xlsx
		        // 2. Crear hoja
		        Sheet hoja = libro.createSheet("EXAMEN");
		        // 3. Crear fila
		        Row fila = hoja.createRow(0);  // fila 0
		        // 4. Crear celda
		        Cell celda = fila.createCell(0); // columna 0
		        // 5. Escribir valor
		        try {
		            // 6. Guardar en fichero
		            FileOutputStream archivo = new FileOutputStream(this.rutaGenerados);
		            libro.write(archivo);
		            archivo.close();
		            libro.close();
		            System.out.println("Excel creado correctamente");
		        } catch (Exception e) {
		            e.printStackTrace();
					System.out.println("FALLO DE GUARDADO DE FICHERO EN ESA URL");
					System.out.println("Errores: "+e.getMessage()+" causados por: "+e.getCause());
					int opcion = JOptionPane.showConfirmDialog(null, "Para continuar, CIÉRRELO y luego decida ¿Desea continuar?", "ADVERTENCIA: Fichero EXCEL abierto", JOptionPane.YES_NO_OPTION);

					if (opcion == JOptionPane.YES_OPTION) {

					} else {
						System.exit(0);  //Sale del programa y termina
					}
		        }
			}
			default:
			{
				break;
			}
		}
	}
	private void exit(int i) {
		// TODO Auto-generated method stub
		
	}
}

//GESTOR DE FICHEROS TXT DOCUMENTOS BLOC DE NOTAS
class gestionFicheros
{
	private String rutaAcceso="C:"+File.separator+"Users"+File.separator+"Sfer4"+File.separator+"Desktop"+File.separator+"AVANCES"+File.separator+"0024_24072026_JAVA_MAVEN"+File.separator+"0001-Proyecto-Maven-POI"+File.separator+"ficherosGenerados"+File.separator;
	private Boolean semaforo=false;
	
	public gestionFicheros(){}
	
	public String getRuta()
	{
		return this.rutaAcceso;
	}
	public Boolean ficheroCreado(String nombreFichero,String formato)
	{
		File ficheroNuevo = new File(this.rutaAcceso+nombreFichero+formato);
		try {
			semaforo=ficheroNuevo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return semaforo;
	}
}
class gestionExamenExcel
{
	private String alumno="";
	private String curso="";
	private String asignatura="";
	private String temario1="";
	private String temario2="";
	private String temario3="";
	private String temario4="";
	private String temario5="";
	private String horarioSeleccionado="";
	private String rutaGenerados="ficherosGenerados/examen.xlsx";
	private String rutaUtilizados="ficherosUtilizados/";
	
	//private Date fecha= new Date();   //Fecha de Java util
	
	private List<String> temariosCompletos= new ArrayList<>();
	private Workbook libro;   //Generando libro para luego ser usado
	private List<String> formularioExamen= new ArrayList<>();
	private Row fila;   //Determinacion de filas para el tratamiento de datos en un EXCEL
	private Cell celda; //Determinacion de la celda para el tratamiento de datos en un EXCEL
	
	File file = new File(this.rutaGenerados);    //getResourceAsStream() para lectura solo de ficheros

	private Date fecha;
	
	public gestionExamenExcel()
	{
		//No hace nada
	}
	public Workbook getLibro()
	{
		return this.libro;
	}
	public void preparacionExamenExcel(String alumno,String curso,String asignatura,String temario1, String temario2, String temario3, String temario4, String temario5, Date fechaExamen, String horarioSeleccionado)
	{
		this.alumno=alumno;
		this.curso=curso;
		this.asignatura=asignatura;
		this.temario1=temario1;
		this.temario2=temario2;
		this.temario3=temario3;
		this.temario4=temario4;
		this.temario5=temario5;
		this.fecha=fechaExamen;
		this.horarioSeleccionado=horarioSeleccionado;
		
		LocalDate fecha = this.fecha.toInstant()
                           .atZone(ZoneId.systemDefault())
                           .toLocalDate();
		this.temariosCompletos.add(temario1);
		this.temariosCompletos.add(temario2);
		this.temariosCompletos.add(temario3);
		this.temariosCompletos.add(temario4);
		this.temariosCompletos.add(temario5);

		this.formularioExamen.add("ALUMNO:");
		this.formularioExamen.add("CURSO:");
		this.formularioExamen.add("ASIGNATURA:");
		this.formularioExamen.add("CONTENIDO 1:");
		this.formularioExamen.add("CONTENIDO 2:");
		this.formularioExamen.add("CONTENIDO 3:");
		this.formularioExamen.add("CONTENIDO 4:");
		this.formularioExamen.add("CONTENIDO 5:");
		this.formularioExamen.add("FECHA:");
		this.formularioExamen.add("HORARIO");
		this.formularioExamen.add("CORREO:");
		this.formularioExamen.add("COMENTARIOS:");
		this.formularioExamen.add("PREGUNTA 1:");
		this.formularioExamen.add("PREGUNTA 2:");
		this.formularioExamen.add("PREGUNTA 3:");
		this.formularioExamen.add("PREGUNTA 4:");
		this.formularioExamen.add("PREGUNTA 5:");
		this.formularioExamen.add("PREGUNTA 6:");
		
		try {			
			InputStream ruta = new FileInputStream(file);
            this.libro = new XSSFWorkbook(ruta);    //Asignacion de ruta
            Sheet hoja = this.libro.getSheet("EXAMEN"); // Ir a la hoja de excel
            CellStyle estilo;  //Generador de estilos para celdas
            Font fuente;	   //Generador de fuentes 
            int i=0;
            
            // Crear estilo con negrita
            estilo = this.libro.createCellStyle();
            fuente = this.libro.createFont();
            fuente.setBold(true);
            estilo.setFont(fuente);
            hoja.setColumnWidth(0, 15 * 256);
            hoja.setColumnWidth(7, 13 * 256);
            
    // A) RELLENANDO EL FORMULARIO DEL EXAMEN
	        if(this.asignatura.equals("Dibujo Técnico"))
	        {
	        	 //EN EL CASO DE QUE SI SEA DIBUJO TÉCNICO LA ASIGNATURA SELECCIONADA
	            for (String formulario : this.formularioExamen) 
	            {
	            	//DATOS DEL FORMULARIO DEL ALUMNO - PRIMERA CARA PARA TODOS LOS EXAMENES
	            	if(i<=11)
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(i);
	                    Cell celda = fila.createCell(0);
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    i++;
	            	}
	            	//PREGUNTAS DE LA PRIMERA CARA
	        		else if(i==12)  
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(18);
	                    Cell celda = fila.createCell(0);
	                    
	                    // Crear estilo con negrita
	                    estilo = this.libro.createCellStyle();
	                    fuente = this.libro.createFont();
	                    fuente.setBold(true);
	                    fuente.setUnderline(Font.U_SINGLE);  // subrayado simple
	                    estilo.setFont(fuente);
	                    
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    celda.setCellStyle(estilo);
	                    i++;	
	            	}
	            	//PREGUNTAS DE LA SEGUNDA CARA Y SIGUIENTES EN CASO O NO DE DIBUJO TECNICO
	            	else if(i>12)
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(51+(i-13)*50);
	                    Cell celda = fila.createCell(0);
	                    
	                    // Crear estilo con negrita
	                    estilo = this.libro.createCellStyle();
	                    fuente = this.libro.createFont();
	                    fuente.setBold(true);
	                    fuente.setUnderline(Font.U_SINGLE);  // subrayado simple
	                    estilo.setFont(fuente);
	                    
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    i++;
	            	} 
	            }
	        }
	        else   //EN EL CASO DE QUE NO SEA DIBUJO TÉCNICO LA ASIGNATURA SELECCIONADA
	        {
	            for (String formulario : this.formularioExamen) 
	            {
	            	//DATOS DEL FORMULARIO DEL ALUMNO - PRIMERA CARA PARA TODOS LOS EXAMENES
	            	if(i<=11)
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(i);
	                    Cell celda = fila.createCell(0);
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    i++;
	            	}
	            	//PREGUNTAS DE LA PRIMERA CARA
	        		else if(i>11 && i<=14)  
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(18+(i-12)*10);
	                    Cell celda = fila.createCell(0);
	                    
	                    // Crear estilo con negrita
	                    estilo = this.libro.createCellStyle();
	                    fuente = this.libro.createFont();
	                    fuente.setBold(true);
	                    fuente.setUnderline(Font.U_SINGLE);  // subrayado simple
	                    estilo.setFont(fuente);
	                    
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    celda.setCellStyle(estilo);
	                    i++;	
	            	}
	            	//PREGUNTAS DE LA SEGUNDA CARA Y SIGUIENTES EN CASO O NO DE DIBUJO TECNICO
	            	else if(i>14)
	            	{
	                    Row fila = hoja.getRow(i);
	                    if (fila == null) fila = hoja.createRow(50+(i-15)*20);
	                    Cell celda = fila.createCell(0);
	                    
	                    // Crear estilo con negrita
	                    estilo = this.libro.createCellStyle();
	                    fuente = this.libro.createFont();
	                    fuente.setBold(true);
	                    fuente.setUnderline(Font.U_SINGLE);  // subrayado simple
	                    estilo.setFont(fuente);
	                    
	                    celda.setCellValue(formulario);
	                    celda.setCellStyle(estilo);
	                    i++;
	            	} 
	            }
	        }

   // B) RELLENANDO EL FORMULARIO DEL EXAMEN CON TODOS LOS DATOS DEL ALUMNO 
            for (i=0;i<11;i++)
            {
            	switch(i)
            	{
	            	case 0:  //NOMBRE ALUMNO
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(alumno);
	            	 break;
	            	}
	            	case 1:  // CURSO DEL ALUMNO
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(curso);
	            	 break;
	            	}
	            	case 2:  // ASIGNATURA DEL EXAMEN
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(asignatura);
	            	 break;
	            	}
	            	case 3,4,5,6,7:   //TEMARIOS DE LA ASIGNATURA
	            	{
	            		//Se colocaon los temarios quitando el curso y la asignatura previa
	                    for (String casilla : this.temariosCompletos)
	                    {
	                       Row fila = hoja.getRow(i);
	                	   if (fila == null) fila = hoja.createRow(i);	
	                       fila.createCell(1).setCellValue(sinPrefijo(casilla));
	                      i++;
	                    }
	                    i--; //Factor correctivo porque por defecto suma una inidad mas a i y luego el bucle for añade otra unidad mas
	            	 break;
		            }
	            	case 8:   //DIA DE LA SEMANA QUE SE EXAMINA Y MES
	            	{
	            		String diaSemana="";
	            		String numeroMes="";
	            	  //Tratamiento previo de la fecha para conglomerarlo mejor
	            		//NUMERO DEL DIA DE LA SEMANA A NOMBRE DEL DIA DE LA SEMANA
	            		switch(fecha.getDayOfWeek().getValue())
	            		{
	            			case 1:{diaSemana="Lunes";break;}
	            			case 2:{diaSemana="Martes";break;}
	            			case 3:{diaSemana="Miércoles";break;}
	            			case 4:{diaSemana="Jueves";break;}
	            			case 5:{diaSemana="Viernes";break;}
	            			case 6:{diaSemana="Sábado";break;}
	            			case 7:{diaSemana="Domingo";break;}
	            			default:{break;}
	            		}
	            	  //NUMERO DEL MES A NOMBRE DEL MES
	            		switch(fecha.getMonthValue())
	            		{
	            			case 1:{numeroMes="Enero";break;}
	            			case 2:{numeroMes="Febrero";break;}
	            			case 3:{numeroMes="Marzo";break;}
	            			case 4:{numeroMes="Abril";break;}
	            			case 5:{numeroMes="Mayo";break;}
	            			case 6:{numeroMes="Junio";break;}
	            			case 7:{numeroMes="Julio";break;}
	            			case 8:{numeroMes="Agosto";break;}
	            			case 9:{numeroMes="Septiembre";break;}
	            			case 10:{numeroMes="Octubre";break;}
	            			case 11:{numeroMes="Noviembre";break;}
	            			case 12:{numeroMes="Diciembre";break;}
	            			default:{break;}
	            		}
	                  String examen= diaSemana+", dia "+fecha.getDayOfMonth()+" de "+numeroMes+" del "+fecha.getYear();
	            	  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(examen);
	            	 break;
	            	}
	            	case 9:  //HORA A LA QUE SE EXAMINA
	            	{
	            		String examenHora= "A las: "+horarioSeleccionado+" horas";
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(examenHora);
	            	 break;
	            	}
	            	default:
	            	{
	            		//Pendiente de definir correos para su posterior modificación y codigo completo
	            		break;
	            	}
            	}
            }

            // PALABRA CALIFICACIÓN (en el formulario procedimiento para darle estilos)
	            // CREAR ESTILO FORMATO NEGRITA
		            CellStyle estiloNegrita = this.libro.createCellStyle();
		            Font fuenteNegrita = this.libro.createFont();
		            fuenteNegrita.setBold(true);
		            estiloNegrita.setFont(fuenteNegrita);
		
		            // Crear o recuperar la fila 0
		            Row fila0 = hoja.getRow(0);
		            if (fila0 == null) fila0 = hoja.createRow(0);
		
		            // Crear la celda y asignar texto
		            Cell celdaCalificacion = fila0.createCell(5);
		            celdaCalificacion.setCellValue("CALIFICACIÓN");
		
		            // Aplicar estilo en negrita
		            celdaCalificacion.setCellStyle(estiloNegrita);
	            
		        // CREAR ESTILO CELDA BORDE FINO PARA LOS COMENTARIOS
		            CellStyle estiloBordeFino = this.libro.createCellStyle();
	
		            // Aplicar borde grueso
		            estiloBordeFino.setBorderBottom(BorderStyle.THIN);

		            //Celda de aplicacion del borde grueso
			        for(int j=12;j<15;j++)
			        {
			            // Crear o recuperar la fila 12, 13 y 14
			            Row filaFina = hoja.getRow(j);
			            if (filaFina == null) filaFina = hoja.createRow(j);
			            	for(i=1; i<8;i++)
			            	{
					            Cell celdaBorde = filaFina.createCell(i);
					            celdaBorde.setCellStyle(estiloBordeFino);
			            	}
			        }
		            
	            // CREAR ESTILO CELDA BORDE GRUESO PARA LA SEPARACION ENTRE EL EXAMEN Y EL FORMULARIO DEL EXAMEN
		            CellStyle estiloBordeGrueso = this.libro.createCellStyle();
	
		            // Aplicar borde grueso
		            estiloBordeGrueso.setBorderBottom(BorderStyle.THICK);
	
		            // Crear o recuperar la fila 16
		            Row fila16 = hoja.getRow(16);
		            if (fila16 == null) fila16 = hoja.createRow(16);
		            
		            //Celda de aplicacion del borde grueso
		            for(i=0; i<8;i++)
	            	{
			            Cell celdaBorde = fila16.createCell(i);
			            celdaBorde.setCellStyle(estiloBordeGrueso);
	            	}
		    
   //C) APLICACION DE ESTILOS A LAS PÁGINAS DE EXCEL
            estiloPagina();   
                        
   //D) INSERCCION DE LOGOS E IMAGENES DE LOS EXAMENES
          //Se rellena el examen: LOGO + las preguntas insertadas como imagen
          //Si es un examen de DIBUJO TÉCNICO el diseño del examen sera otro: TRUE           
	        if(this.asignatura.equals("Dibujo Técnico"))
	 	        {
	        		rellenarExamen(this.temariosCompletos,true);
	 	        }else
	 	        {
         //Si es un examen de otro tipo distinto a DIBUJO TECNICO el diseño del examen sera el estandar: FALSE
	        		rellenarExamen(this.temariosCompletos,false);
	 	        }
        	FileOutputStream archivo= new FileOutputStream(file);
            libro.write(archivo);
            archivo.close();
            libro.close();
            System.out.println("Excel editado correctamente");	 
			JOptionPane.showMessageDialog(null, "Fichero EXCEL creado. Búsquelo en el directorio 'ficherosGenerados'");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("El error del caso 1 ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
	        System.out.println("Se debera crear el fichero de EXCEL previamente");
	    }
	}
	//SE CONFIGURA LA PAGINA DE LA HOJA DEL EXCEL SELECCIONADA
	public void estiloPagina()
	{
		Sheet hoja = this.libro.getSheet("EXAMEN"); // Ir a la hoja de excel

		// Configuración de impresión
		PrintSetup ps = hoja.getPrintSetup();
		ps.setLandscape(false);                     // vertical
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE);  // Tamaño A4

		// márgenes en pulgadas
		hoja.setMargin(PageMargin.LEFT, 0.7);
		hoja.setMargin(PageMargin.RIGHT, 0.7);
		hoja.setMargin(PageMargin.TOP, 0.76);
		hoja.setMargin(PageMargin.BOTTOM, 0.76);
		hoja.setMargin(PageMargin.HEADER, 0.3);
		hoja.setMargin(PageMargin.FOOTER, 0.3);

		// Encabezado y pie de página
		Header header = hoja.getHeader();
		header.setCenter("PRUEBA DE EVALUACION ARCES 3 FORMACION");

		Footer footer = hoja.getFooter();
		footer.setRight("Página &P de &N");

		// Ajuste de escala
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)1);
	}
	
	//AREA DE CONSTRUCCION DEL EXAMEN CON EL LOGO Y LAS PREGUNTAS
	//TODOS LOS EXAMENES SERÁN IGUALES SALVO EL DE DIBUJO TÉCNICO CUYAS PREGUNTAS DE CONSIDERABLE EXTENSIÓN DE TRAZADO
	//      OBLIGARÁ A QUE HAYA UNA PREGUNTA POR PÁGINA, NO VARIAS PREGUNTAS EN UNA PAGINA COMO ESTABA HECHO EN ESTADAR
	
	//SE INSERTAN EL LOGO Y LAS IMAGENES DE LOS CONTENIDOS ELEGIDOS DE LAS CARPETAS
	public void rellenarExamen(List<String> temariosExamen, boolean examenDibujo)
	{
		//Definimos la hoja del libro EXCEL y un unico "dibujo" (patriarch) para toda la hoja
		Sheet hoja = this.libro.getSheet("EXAMEN");
		CreationHelper helper = this.libro.getCreationHelper();
		Drawing<?> dibujo = hoja.createDrawingPatriarch();   //un solo patriarch: se reutiliza para todas las imagenes

	//1) COLOCACION DE LOS LOGOS: el mismo logo en la esquina inferior derecha de cada una de las dos hojas/paginas
		//NO ES EXAMEN DE DIBUJO TECNICO
		byte[] bytesLogo = cargarBytesImagen(this.rutaUtilizados + "A3FLogo.jpg");
		if (bytesLogo != null)
		{
			int numeroLogos=0;
			if(examenDibujo)
			{
				//ES EXAMEN DE DIBUJO TECNICO
				numeroLogos = 6;   //una copia del logo por cada pagina impresa
			}
			else
			{
				numeroLogos = 2;   //una copia del logo por cada pagina impresa
			}
			for (int i = 0; i < numeroLogos; i++)
			{
				int idxLogo = this.libro.addPicture(bytesLogo, Workbook.PICTURE_TYPE_JPEG);
				ClientAnchor anclaLogo = helper.createClientAnchor();
				anclaLogo.setCol1(6);            // columna inicial (parte derecha de la hoja)
				anclaLogo.setRow1(47 + i * 50);  // fila inicial: pie de la pagina 1 y de la pagina 2
				anclaLogo.setCol2(7);
				anclaLogo.setRow2(49 + i * 50);
				anclaLogo.setDx1(0);
				anclaLogo.setDy1(0);
				anclaLogo.setDx2(Units.toEMU(158));   // ancho en pixeles
				anclaLogo.setDy2(Units.toEMU(65));     // alto en pixeles
				dibujo.createPicture(anclaLogo, idxLogo);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(
				    null,
				    "AVISO: no se pudo acceder al logo en " + this.rutaUtilizados + "A3FLogo.jpg (se continua sin logo). Además, el programa no puede encontrar el LOGO que se iba a implementar. Se seguirá editando sin él.",
				    "Información",
				    JOptionPane.INFORMATION_MESSAGE
				);
		}

	//2) COLOCACION DE UNA IMAGEN ALEATORIA (SIN REPETIR) DEBAJO DE CADA "PREGUNTA 1..6"
		//Filas donde estan las etiquetas PREGUNTA 1..6 (deben coincidir con preparacionExamenExcel):
		int filasPreguntas[]= {0,0,0,0,0,0};
		if(examenDibujo)
		{
			// PREGUNTA 1->18  (primera cara)
			// PREGUNTA 2->51  (segunda cara)
			// PREGUNTA 3->101 (tercera cara)
			// PREGUNTA 4->151 (cuarta cara)
			// PREGUNTA 5->201 (quinta cara)
			// PREGUNTA 6->251 (sexta cara)
			filasPreguntas[0]=18;
			filasPreguntas[1]=51;
			filasPreguntas[2]=101;
			filasPreguntas[3]=151;
			filasPreguntas[4]=201;
			filasPreguntas[5]=251;
		}
		else{
			//   PREGUNTA 1->18, 2->28, 3->38 (primera cara) y 4->50, 5->70, 6->90 (segunda cara)
			filasPreguntas[0]=18;
			filasPreguntas[1]=28;
			filasPreguntas[2]=38;
			filasPreguntas[3]=50;
			filasPreguntas[4]=70;
			filasPreguntas[5]=90;
		}
		int maxAnchoPx = 380;   //ancho maximo: poco mas de media pagina A4 en vertical (~660px utiles)

		//Para cada carpeta de contenido preparamos una "baraja" de sus imagenes, para ir sacando
		//una al azar y sin repetir. Varias PREGUNTAS de la misma carpeta comparten la misma baraja.
		Map<String, Deque<File>> barajaPorCarpeta = new HashMap<>();
		List<String> carpetasValidas = new ArrayList<>();   //respeta el orden y el numero de contenidos elegidos
		for (String temario : temariosExamen)
		{
			if (temario == null || temario.trim().isEmpty())
			{
				continue;   //no hay contenido seleccionado en esa casilla
			}
			if (!barajaPorCarpeta.containsKey(temario))
			{
				//Cada temario se corresponde con una carpeta con el mismo nombre dentro de ficherosUtilizados
				File carpeta = new File(this.rutaUtilizados + temario);
				if (!carpeta.exists() || !carpeta.isDirectory())
				{
					JOptionPane.showMessageDialog(
						    null,
						    "AVISO: carpeta de contenido no encontrada -> " + carpeta.getPath(),
						    "Información",
						    JOptionPane.INFORMATION_MESSAGE
						);
					continue;   //resolvemos el fallo de acceso saltando el contenido inexistente
				}
				File[] archivos = carpeta.listFiles(f -> f.isFile() && esImagen(f.getName()));
				if (archivos == null || archivos.length == 0)
				{
					JOptionPane.showMessageDialog(
						    null,
						    "AVISO: la carpeta no contiene imagenes -> " + carpeta.getPath(),
						    "Información",
						    JOptionPane.INFORMATION_MESSAGE
						);
					continue;   //carpeta vacia: se salta sin romper la ejecucion
				}
				List<File> lista = new ArrayList<>(Arrays.asList(archivos));
				Collections.shuffle(lista);   //orden aleatorio: sacaremos las imagenes de una en una sin repetir
				barajaPorCarpeta.put(temario, new ArrayDeque<>(lista));
			}
			carpetasValidas.add(temario);
		}

		if (carpetasValidas.isEmpty())
		{
			JOptionPane.showMessageDialog(
				    null,
				    "AVISO: no hay ninguna carpeta con imagenes para las preguntas",
				    "Información",
				    JOptionPane.INFORMATION_MESSAGE
				);
			return;
		}

		//Recorremos las 6 preguntas y colocamos UNA imagen aleatoria debajo de cada etiqueta
		for (int p = 0; p < filasPreguntas.length; p++)
		{
			//La pregunta p usa la carpeta correspondiente (se van rotando las carpetas disponibles)
			String carpeta = carpetasValidas.get(p % carpetasValidas.size());
			Deque<File> baraja = barajaPorCarpeta.get(carpeta);
			//Si esa carpeta ya agoto sus imagenes, buscamos otra carpeta que aun tenga (para no repetir)
			if (baraja == null || baraja.isEmpty())
			{
				baraja = null;
				for (String c : carpetasValidas)
				{
					if (!barajaPorCarpeta.get(c).isEmpty())
					{
						baraja = barajaPorCarpeta.get(c);
						break;
					}
				}
				if (baraja == null)
				{
					break;   //no quedan imagenes sin usar en ninguna carpeta
				}
			}
			File imagen = baraja.poll();   //imagen elegida al azar y que no se repetira
			byte[] bytesImagen = cargarBytesImagen(imagen.getPath());
			if (bytesImagen == null)
			{
				continue;   //si esa imagen falla al leerse, dejamos esa pregunta sin imagen
			}

			//Calculamos el tamaño respetando el ancho maximo (sin agrandar imagenes pequeñas)
			int anchoPx = maxAnchoPx;
			int altoPx = (int) Math.round(maxAnchoPx * 0.6);   //relacion por defecto si no se puede medir
			int[] dim = dimensionesImagen(bytesImagen);
			if (dim != null && dim[0] > 0 && dim[1] > 0)
			{
				double escala = Math.min(1.0, (double) maxAnchoPx / dim[0]);
				anchoPx = (int) Math.round(dim[0] * escala);
				altoPx = (int) Math.round(dim[1] * escala);
			}

			int fila = filasPreguntas[p] + 1;   //justo DEBAJO de la etiqueta de la pregunta
			int idxImagen = this.libro.addPicture(bytesImagen, tipoImagen(imagen.getName()));
			ClientAnchor anclaImagen = helper.createClientAnchor();
			//Tamaño absoluto en pixeles, independiente del ancho de las columnas

				anclaImagen.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
				anclaImagen.setCol1(0);
				anclaImagen.setRow1(fila);
				anclaImagen.setCol2(5);  //Anchura de 5 celdas a la derecha por defecto para cualquier imagen
				
				
				if(examenDibujo)   //ES EXAMEN DIBUJO TECNICO
				{	
				anclaImagen.setRow2(fila+12);  //Altura de 5 celdas hacia abajo para cualquier imagen
				}
				else			  //NO ES EXAMEN DIBUJO TECNICO
				{
					anclaImagen.setRow2(fila+5);  //Altura de 5 celdas hacia abajo para cualquier imagen
				}
				anclaImagen.setDx1(0);
				anclaImagen.setDy1(0);
				anclaImagen.setDx2(Units.pixelToEMU(anchoPx));
				anclaImagen.setDy2(Units.pixelToEMU(altoPx));
				dibujo.createPicture(anclaImagen, idxImagen);	
		}
 }
	

	//Lee de forma segura los bytes de una imagen; devuelve null si no se puede acceder a ella
	private byte[] cargarBytesImagen(String ruta)
	{
		File fichero = new File(ruta);
		if (!fichero.exists() || !fichero.isFile())
		{
			JOptionPane.showMessageDialog(
				    null,
				    "AVISO: imagen no accesible -> " + ruta,
				    "Información",
				    JOptionPane.INFORMATION_MESSAGE
				);
			return null;
		}
		try (InputStream entrada = new FileInputStream(fichero))
		{
			return entrada.readAllBytes();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(
				    null,
				    "AVISO: fallo al leer la imagen -> " + ruta + " (" + e.getMessage() + ")",
				    "Información",
				    JOptionPane.INFORMATION_MESSAGE
				);
			return null;
		}
	}

	//Indica si el nombre de fichero corresponde a un formato de imagen admitido
	private boolean esImagen(String nombre)
	{
		String n = nombre.toLowerCase();
		return n.endsWith(".jpg") || n.endsWith(".jpeg") || n.endsWith(".png");
	}

	//Traduce la extension del fichero al tipo de imagen que entiende Apache POI
	private int tipoImagen(String nombre)
	{
		if (nombre.toLowerCase().endsWith(".png"))
		{
			return Workbook.PICTURE_TYPE_PNG;
		}
		return Workbook.PICTURE_TYPE_JPEG;   //jpg y jpeg
	}

	//Devuelve {ancho, alto} en pixeles de la imagen para poder escalarla; null si no se puede medir
	private int[] dimensionesImagen(byte[] bytes)
	{
		try
		{
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
			if (img == null)
			{
				return null;
			}
			return new int[] { img.getWidth(), img.getHeight() };
		}
		catch (IOException e)
		{
			return null;
		}
	}

	//Quita el prefijo "NIVEL -CODIGO------ " del temario de forma segura (evita StringIndexOutOfBounds)
	private String sinPrefijo(String temario)
	{
		if (temario == null)
		{
			return "";
		}
		return temario.length() > 19 ? temario.substring(19) : temario;
	}
}