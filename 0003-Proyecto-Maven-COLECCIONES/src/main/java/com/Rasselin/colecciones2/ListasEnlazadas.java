package com.Rasselin.colecciones2;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ListIterator;

public class ListasEnlazadas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String intervenientes[]= new String[2];
		intervenientes[0]="William Wissangel";
		intervenientes[1]="Sharyllin Rousher";

		Eventos acontecimiento1= new Eventos("Froja de Ambur",LocalDate.of(1950, 9, 25),intervenientes);
		Eventos acontecimiento2= new Eventos("Froja de Noctis Ambrae",LocalDate.of(1955, 4, 3),intervenientes);
		Eventos acontecimiento3= new Eventos("Froja de Manpertos",LocalDate.of(1960, 5, 5),intervenientes);
		Eventos acontecimiento4= new Eventos("Froja de La Tierra",LocalDate.of(1956, 10, 30),intervenientes);
		Eventos acontecimiento5= new Eventos("Froja de La Luna",LocalDate.of(1970, 11, 11),intervenientes);

		LinkedList<Eventos> listaEventos= new LinkedList<Eventos>();
		listaEventos.add(acontecimiento1);
		listaEventos.add(acontecimiento2);
		listaEventos.add(acontecimiento3);
		listaEventos.add(acontecimiento4);
		
		ListIterator<Eventos> it= listaEventos.listIterator();
		
		System.out.println("");
		System.out.println("");
		System.out.println("CON LISTAS ENLAZADAS");
		System.out.println("");
		for(Eventos Eventos: listaEventos)
		{
			System.out.println(Eventos.getSuceso());
		}
		System.out.println("");
		System.out.println("CON ITERADOR SOBRE LISTAS ENLAZADAS");
		System.out.println("");
		while(it.hasNext())
		{
			System.out.println(it.next().getSuceso());
		}
		System.out.println("");
		System.out.println("METIENDO OTRO ELEMENTO MAS EN LA LISTA ENLAZADA");
		System.out.println("");
		int semaforo=0;
		while(it.previousIndex()>0)
		{		
			if(it.nextIndex()==2 && semaforo==0)
			{
				it.add(acontecimiento5);
				semaforo=1;
			}
			it.previous();  //Mover el iterador una posicion adelante
		}
		it.previous();  //Mover el iterador una posicion adelante

		while(it.hasNext())
		{
			System.out.println(it.next().getSuceso());
		}
	}

}

class Eventos
{
	private String suceso="";
	private LocalDate fecha;
	private String personajes[];
	
	public Eventos(String suceso, LocalDate fecha,String personajes[])
	{
		this.suceso=suceso;
		this.fecha=fecha;
		this.personajes=personajes;
	}
	public String getSuceso()
	{
		return this.suceso;
	}
	public LocalDate getFecha()
	{
		return this.fecha;
	}
	public String[] getPersonajes()
	{
		return this.personajes;
	}
}