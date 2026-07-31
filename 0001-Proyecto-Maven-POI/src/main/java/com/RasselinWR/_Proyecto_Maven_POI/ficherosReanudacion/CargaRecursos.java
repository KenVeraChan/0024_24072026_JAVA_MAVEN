package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.PageMargin;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
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
		File file = new File("ficherosGenerados/examen.xlsx");    //getResourceAsStream() para lectura solo de ficheros

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
		        		
		        	 FileInputStream fis = new FileInputStream("ficherosUtilizados/AlumnosArces3Formacion.xlsm");
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
		        celda.setCellValue("Hola Excel");

		        try {
		            // 6. Guardar en fichero
		            FileOutputStream archivo = new FileOutputStream("ficherosGenerados/examen.xlsx");
		            libro.write(archivo);
		            archivo.close();
		            libro.close();
		            System.out.println("Excel creado correctamente");
		        } catch (Exception e) {
		            e.printStackTrace();
					System.out.println("FALLO DE GUARDADO DE FICHERO EN ESA URL");
					System.out.println("Errores: "+e.getMessage()+" causados por: "+e.getCause());
		        }
			}
			default:
			{
				break;
			}
		}
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
	
	//private Date fecha= new Date();   //Fecha de Java util
	
	private List<String> temariosCompletos= new ArrayList<>();
	private Workbook libro;   //Generando libro para luego ser usado
	private List<String> formularioExamen= new ArrayList<>();
	private Row fila;   //Determinacion de filas para el tratamiento de datos en un EXCEL
	private Cell celda; //Determinacion de la celda para el tratamiento de datos en un EXCEL
	
	File file = new File("ficherosGenerados/examen.xlsx");    //getResourceAsStream() para lectura solo de ficheros

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
		
		try {			
			InputStream ruta = new FileInputStream(file);
            this.libro = new XSSFWorkbook(ruta);    //Asignacion de ruta
            Sheet hoja = libro.getSheet("EXAMEN"); // Ir a la hoja de excel
            CellStyle estilo;  //Generador de estilos para celdas
            Font fuente;	   //Generador de fuentes 
            int i=0;
            
            // Crear estilo con negrita
            estilo = libro.createCellStyle();
            fuente = libro.createFont();
            fuente.setBold(true);
            estilo.setFont(fuente);
            hoja.setColumnWidth(0, 15 * 256);
            hoja.setColumnWidth(7, 13 * 256);
            
         // RELLENANDO EL FORMULARIO DEL EXAMEN
            for (String formulario : this.formularioExamen) 
            {
                Row fila = hoja.getRow(i);
                if (fila == null) fila = hoja.createRow(i);
                Cell celda = fila.createCell(0);
                celda.setCellValue(formulario);
                celda.setCellStyle(estilo);
                i++;
            }

            // RELLENANDO TEMARIOS COMPLETOS
            for (i=0;i<10;i++)
            {
            	switch(i)
            	{
	            	case 0:
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(alumno);
	            	 break;
	            	}
	            	case 1:
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(curso);
	            	 break;
	            	}
	            	case 2:
	            	{
	                  Row fila = hoja.getRow(i);
	                  if (fila == null) fila = hoja.createRow(i);	
	                  fila.createCell(1).setCellValue(asignatura);
	            	 break;
	            	}
	            	case 3,4,5,6,7:
	            	{
	                    for (String casilla : this.temariosCompletos)
	                    {
	                       Row fila = hoja.getRow(i);
	                	   if (fila == null) fila = hoja.createRow(i);	
	                       fila.createCell(1).setCellValue(casilla.substring(19));
	                      i++;
	                    }
	                    i--; //Factor correctivo porque por defecto suma una inidad mas a i y luego el bucle for añade otra unidad mas
	            	 break;
		            }
	            	case 8:
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
	                  System.out.println("Siguiente Case es el: Case"+i);
	            	 break;
	            	}
	            	case 9:
	            	{
	            		String examenHora= "A las: "+horarioSeleccionado;
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
	            // Crear estilo con negrita
	            CellStyle estiloNegrita = libro.createCellStyle();
	            Font fuenteNegrita = libro.createFont();
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
            
            estiloPagina();   //Se modifican los parametros de la hoja en referente a estilos
            
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
}