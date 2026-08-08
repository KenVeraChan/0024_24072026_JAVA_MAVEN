package com.Rasselin.colecciones3;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class ArbolesEnlazados {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//TREESET DE STRINGS
		TreeSet<String> arbolPaises= new TreeSet<String>(Comparator.reverseOrder());
		arbolPaises.add("España");
		arbolPaises.add("Noruega");
		arbolPaises.add("Francia");
		arbolPaises.add("Belgica");
		arbolPaises.add("India");
		arbolPaises.add("Nueva Zelanda");
		
		for(String pais: arbolPaises)
		{
			System.out.println(pais);
		}


		//TREESET DE OBJETOS
		
		Iterator<String> listas= arbolPaises.iterator();
		Personas p1= new Personas("Rasselin","Wissangel Rousher",21);
		Personas p2= new Personas("Emiliam","Bastreriz",23);
		Personas p3= new Personas("Verduliz","Sainz",20);
		Personas p4= new Personas("Vitrea","Horizian",22);

		Personas comparador= new Personas();
		TreeSet<Personas> ordenarEdades= new TreeSet<Personas>(comparador);
		ordenarEdades.add(p1);
		ordenarEdades.add(p2);
		ordenarEdades.add(p3);
		ordenarEdades.add(p4);
		for(Personas Personas: ordenarEdades)
		{
			System.out.println(Personas.getEdad());
		}
		

	}

}

class Personas implements Comparable<Personas>, Comparator<Personas>
{
	private String nombres;
	private String apellidos;
	private int edad;
	
	public Personas()
	{
		
	}
	
	public Personas(String nombres, String apellidos, int edad)
	{
		this.nombres=nombres;
		this.apellidos=apellidos;
		this.edad=edad;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	//METODO DE LA INTERFAZ COMPARABLE
	public int compareTo(Personas o) {
		Personas personaForanea=(Personas)o;  //Casteando
		return personaForanea.edad-this.edad;
	}

	@Override
	//METODO DE LA INTERFAZ COMPARATOR
	public int compare(Personas o1, Personas o2) {
		int caracter1=o1.getApellidos().length();
		int caracter2=o2.getApellidos().length();
		if(caracter1<caracter2) return -1;
		if(caracter1>caracter2) return 1;
		else return 0;
	}
	
}


