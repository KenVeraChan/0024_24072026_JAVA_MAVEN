package com.Rasselin.conexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World DE NUEVO!");
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Quieres guardar los cambios?",
                "Guardar",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        switch (opcion) {
            case JOptionPane.YES_OPTION:
                System.out.println("Guardar");
                Universidad persona= new Universidad();
                persona.setAlumnoAdmitido();  //Guardado en fichero
                break;
            case JOptionPane.NO_OPTION:
                System.out.println("No guardar, expone lo guardado, cargandolo del fichero");
                Colegio registro= new Colegio();
                registro.gestionEstudiantes();
                break;
            case JOptionPane.CANCEL_OPTION:
            case JOptionPane.CLOSED_OPTION:
                System.out.println("Cancelar operación");
                break;
        }
    }
}
class Universidad
{
	public Universidad()
	{
		
	}
	public void setAlumnoAdmitido()
	{
		//Declaramos el registro del alumno admitido
        Alumnos estudiante= new Alumnos("Rasselin","Wissangel Rousher",22,9.87);
        //Guardamos el registro en local del alumno admitido
        try {
			ObjectOutputStream fich= new ObjectOutputStream(new FileOutputStream("ficherosUtilizados/alumnos.dat"));
				fich.writeObject(estudiante);
				fich.close();
			System.out.println("Alumno registrado");
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class Colegio extends Alumnos
{
	private boolean existe=false;
	
	public Colegio()
	{

	}
	public void gestionEstudiantes()
	{
		try {
			ObjectInputStream enlace= new ObjectInputStream(new FileInputStream("ficherosUtilizados/alumnos.dat"));
				try {
					Alumnos identificacion= (Alumnos)enlace.readObject();
					System.out.println("Su identificacion es: "+identificacion.getNombre()+" "+identificacion.getApellidos());
					enlace.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					System.out.println("El error (DE CLASE NO ENCONTRADA) ha sido: "+e.getMessage()+ ", debido a: "+e.getCause());
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("El error (DE FICHERO NO ENCONTRADO) ha sido: "+e.getMessage()+ ", debido a: "+e.getCause());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("El error (DE GESTION ENTRADAS SALIDAS) ha sido: "+e.getMessage()+ ", debido a: "+e.getCause());

		}

	}
}

class Alumnos implements Serializable
{
	private String nombre;
	private String apellidos;
	private int edad;
	private double calificacion;
	
	public Alumnos()
	{
		
	}
	
	public Alumnos(String nombre, String apellidos, int edad, double calificacion)
	{
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.edad=edad;
		this.calificacion=calificacion;
	}
	public String getNombre()
	{
		return this.nombre;
	}
	public String getApellidos()
	{
		return this.apellidos;
	}
	public int getEdad()
	{
		return this.edad;
	}
	public double getCalificacion()
	{
		return this.calificacion;
	}
}