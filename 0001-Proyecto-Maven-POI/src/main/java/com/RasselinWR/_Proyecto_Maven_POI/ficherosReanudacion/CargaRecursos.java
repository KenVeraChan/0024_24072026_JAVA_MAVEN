package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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