package com.Rasselin.colecciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    
        BancoColecciones cliente1= new BancoColecciones("Rasselin Wissangel Rousher","0001",2010.34);
        BancoColecciones cliente2= new BancoColecciones("Vitrea Horiz","0002",1010.34);
        BancoColecciones cliente3= new BancoColecciones("Emiliam Bastreriz","0003",1510.34);
        BancoColecciones cliente4= new BancoColecciones("Verduliz Sainz","0004",1210.34);
        
        //Creando coleccion porque admite generico
        Set<BancoColecciones> clientesBanco = new HashSet<BancoColecciones>();
        clientesBanco.add(cliente1);
        clientesBanco.add(cliente2);
        clientesBanco.add(cliente3);
        clientesBanco.add(cliente4);
        
        Iterator<BancoColecciones> it= clientesBanco.iterator();
        int i=0;
        while(it.hasNext())
        {
        	String nombre=it.next().getNombreUsuario();
        	String cuenta=it.next().getNumCuenta();
        	double saldo= it.next().getSaldo();
        	System.out.println(i+")"+saldo);
        	i++;
        }
        
        for(BancoColecciones cliente: clientesBanco)
        {
        	System.out.println("Nombre: "+cliente.getNombreUsuario()+". Su cuenta es: "+cliente.getNumCuenta()+" y tiene de saldo: "+cliente.getSaldo());
        }
        Libros libro1= new Libros("Vitrea Horiz","Vida de dia","000111");
        Libros libro2= new Libros("Vitrea Horiz","Vida de dia","000111");
        
        if(libro1.equals(libro2))
        {
        	System.out.println("SON IGUALES");
        }
        else
        {
        	System.out.println("NO SON IGUALES");
        }
        
        /*
        ArrayList<Acontecimientos> episodios= new ArrayList<Acontecimientos>();
        String personajes[]= {"William Wissangel","Sharyllin Rousher"};
        
        episodios.add(new Acontecimientos(0,"Primer Gobierno en Shunay",LocalDate.of(1960,1,20), personajes));
        episodios.add(new Acontecimientos(1,"Segundo Gobierno en Shunay",LocalDate.of(1970,1,20), personajes));
        episodios.add(new Acontecimientos(2,"Tercer Gobierno en Shunay",LocalDate.of(1980,1,20), personajes));
        episodios.add(new Acontecimientos(3,"Cuarto Gobierno en Shunay",LocalDate.of(1990,1,20), personajes));
        episodios.add(new Acontecimientos(4,"Quinto Gobierno en Shunay",LocalDate.of(2000,1,20), personajes));
        episodios.add(new Acontecimientos(5,"Sexto Gobierno en Shunay",LocalDate.of(2010,1,20), personajes));
        episodios.add(new Acontecimientos(6,"Septimo Gobierno en Shunay",LocalDate.of(2020,1,20), personajes));
        //System.out.println(Acontecimientos.todosAcontecimientos(episodios));
        
        Iterator<Acontecimientos> iteradorCapitulos= episodios.iterator();
        //System.out.println("Tamanio del ArrayLista es: "+episodios.size());
        //while(iteradorCapitulos.hasNext()) {System.out.println(iteradorCapitulos.next().toString());}
        
        //ESTUDIO DE LONGITUD DE ARRAYS DE TODO TIPO DE DATOS
        String verduras[]= {"lechuga","tomate","pepinillos","coliflor","aceitunas","acacias"};
        int salarios[]= {2300,1201,4302,3300,2010,4400,2021,3239,4442,5533};
        Double calificaciones[]= {7.8,5.6,3.4,5.5,9.8};
        Object detalle[]= episodios.toArray();
        
        String calendarios[]= new String[Acontecimientos.fechasAcontecimientos(episodios).length];
        for(int i=0;i<Acontecimientos.fechasAcontecimientos(episodios).length;i++)
        {
        	calendarios[i]=Acontecimientos.fechasAcontecimientos(episodios)[i];
        }
        
        //System.out.println("Longitud del vector STRINGS es: "+ProgramacionGenerica.estudiaArrays(verduras));
        //System.out.println("Longitud del vector de DOUBLES es: "+ProgramacionGenerica.estudiaArrays(calificaciones));
        //System.out.println("Longitud del vector de OBJETOS es: "+ProgramacionGenerica.estudiaArrays(detalle));
        
        //System.out.println("El primer elemento del vector STRINGS es: "+ProgramacionGenerica.devolverMinimo(verduras));
        //System.out.println("El primer elemento del vector de DOUBLES es: "+ProgramacionGenerica.devolverMinimo(calificaciones));
        //System.out.println("El primer elemento del vector de OBJETOS es: "+ProgramacionGenerica.devolverMinimo(calendarios));
		*/
    }
}


class Acontecimientos
{
	private int ID=0;
	private String suceso="";
	private int day;
	private String dayofWeek;
	private String month;
	private int monthInt;
	private int year;
	private String personajes[];
	
	public Acontecimientos()
	{
		
	}

	public Acontecimientos(int ID, String suceso, LocalDate fechaSuceso, String[] personajes) 
	{
		this.ID=ID;
		this.day=fechaSuceso.getDayOfMonth();

		switch(fechaSuceso.getDayOfWeek().getValue())
		{
			case 1:{this.dayofWeek="Lunes";	break;}
			case 2:{this.dayofWeek="Martes"; break;}
			case 3:{this.dayofWeek="Miércoles";	break;}
			case 4:{this.dayofWeek="Jueves"; break;}
			case 5:{this.dayofWeek="Viernes"; break;}
			case 6:{this.dayofWeek="Sábado"; break;}
			case 7:{this.dayofWeek="Domingo"; break;}
			default:{ break;}
		}		
		this.monthInt=fechaSuceso.getMonth().getValue();
		switch(fechaSuceso.getMonth().getValue())
		{
			case 1:{this.month="enero";	break;}
			case 2:{this.month="febrero"; break;}
			case 3:{this.month="marzo";	break;}
			case 4:{this.month="abril";	break;}
			case 5:{this.month="mayo";	break;}
			case 6:{this.month="junio";	break;}
			case 7:{this.month="julio";	break;}
			case 8:{this.month="agosto"; break;}
			case 9:{this.month="septiembre"; break;}
			case 10:{this.month="octubre";	break;}
			case 11:{this.month="noviembre"; break;}
			case 12:{this.month="diciembre"; break;}
			default:{ break;}
		}
		this.year=fechaSuceso.getYear();
		this.personajes=personajes;
	}
	public static String todosAcontecimientos(ArrayList<Acontecimientos> paginasLibro)
	{
		int longitudLibro= paginasLibro.size();
		String calendario[]= new String[longitudLibro];		
		
		for(int i=0;i<calendario.length;i++)
		{
			System.out.println("Suceso "+i+") "+
					paginasLibro.get(i).suceso+" el dia "+
					paginasLibro.get(i).dayofWeek+", "+
					paginasLibro.get(i).day+" de "+
					paginasLibro.get(i).month+" de "+
					paginasLibro.get(i).year+" y los personajes implicados fueron: "+
					paginasLibro.get(i).personajes[0]+" y "+
					paginasLibro.get(i).personajes[1]+"."
			);
		}
		//AL SER ESTÁTICO NO PRECISA DE INSTANCIACION PORQUE ES UN METODO DE LA CLASE NO DEL OBJETO
		return "";
	}
	public static String[] fechasAcontecimientos(ArrayList<Acontecimientos> paginasLibro)
	{
		int longitudLibro= paginasLibro.size();
		String calendario[]= new String[longitudLibro];		
		
		for(int i=0;i<calendario.length;i++)
		{
			calendario[i]=
					paginasLibro.get(i).day+"/"+
					paginasLibro.get(i).month+"/"+
					paginasLibro.get(i).year;
		}
		//AL SER ESTÁTICO NO PRECISA DE INSTANCIACION PORQUE ES UN METODO DE LA CLASE NO DEL OBJETO
		return calendario;
	}
}

