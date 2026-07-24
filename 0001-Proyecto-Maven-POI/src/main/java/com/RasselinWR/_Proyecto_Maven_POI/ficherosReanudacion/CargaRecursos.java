package com.RasselinWR._Proyecto_Maven_POI.ficherosReanudacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private String nombreFichero="ejemplo";   //NOMBRE PARA CUALQUIER FICHERO
	private String formatoFichero=".xlsx";    //TIPO PARA EXCEL
	private String rutaExcel="";
	
	public CargaRecursos(){}
	
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
		if(selector==1)
		{
        	//ESCRIBIR EN FICHERO EXCEL
	        try {
	            Workbook libro = new XSSFWorkbook(); // archivo .xlsx
	            Sheet hoja = libro.createSheet("Datos");

	            Row fila = hoja.createRow(0);        // fila 0
	            Cell celda = fila.createCell(0);     // columna 0

	            celda.setCellValue(123.45);          // n�mero en la celda

	            FileOutputStream archivo= new FileOutputStream(new File(new gestionFicheros().getRuta()+this.nombreFichero+this.formatoFichero));
	            //FileOutputStream archivo = new FileOutputStream("ejemplo.xlsx");
	            libro.write(archivo);
	            archivo.close();
	            libro.close();

	            System.out.println("Excel creado correctamente");
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("El error ha sido: "+e.getMessage()+" y la causa: "+e.getCause());
	        }
		}
		if(selector==2)
		{
	        //LEER DE UN FICHERO DE EXCEL
	        this.rutaExcel = new gestionFicheros().getRuta()+this.nombreFichero+this.formatoFichero;  // ruta del fichero

	        List<String> datosColumna = new ArrayList<>();

	        try (FileInputStream fis = new FileInputStream(this.rutaExcel);
	             Workbook workbook = new XSSFWorkbook(fis)) {

	            Sheet hoja = workbook.getSheetAt(0); // primera hoja del Excel

	            for (Row fila : hoja) {
	                Cell celda = fila.getCell(0); // primera columna (índice 0)

	                if (celda != null) {    //Si es distinto a NULL podra guardar el dato
	                    datosColumna.add(celda.toString());   //Adiciona el dato
	                }
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // MOSTRAR RESULTADOS
	        System.out.println("Datos leídos de la primera columna:");
	        for (String dato : datosColumna) {
	            System.out.println(dato);
	        }
		}
		if(selector==3)
		{
			gestionFicheros fichero= new gestionFicheros();
			if(fichero.ficheroCreado("Facturas",".txt"))
			{
				System.out.println("Fichero creado correctamente");
			}
			else 
			{
				System.out.println("Error de creacion de fichero");
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