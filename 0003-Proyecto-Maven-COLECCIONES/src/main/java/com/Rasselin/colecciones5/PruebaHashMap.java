package com.Rasselin.colecciones5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class PruebaHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Almacen nave= new Almacen();
		nave.pruebaHashMap();
		int USERS= Integer.parseInt(JOptionPane.showInputDialog("Indique el número de usuarios que se van a conectar"),10);
		nave.pruebaHashMap2(USERS);
	}

}

class Almacen
{
	private String codigo="";
	private LocalDate fecha;
	private String productos="";
	
	public Almacen()
	{
		
	}
	
	public Almacen(String codigo, LocalDate fecha, String productos)
	{
		this.codigo=codigo;
		this.fecha=fecha;
		this.productos=productos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}
	public void pruebaHashMap()
	{
		HashMap<Integer,Almacen> listaRegistros= new HashMap<Integer,Almacen>();
		listaRegistros.put(1, new Almacen("N12P20AREA1FIL23COL34",LocalDate.of(2026, 12, 5),"Teclados ordenador"));
		listaRegistros.put(2, new Almacen("N01P22AREA2FIL25COL40",LocalDate.of(2025, 11, 15),"Figrorifico industrial"));
		listaRegistros.put(3, new Almacen("N03P22AREA1FIL23COL34",LocalDate.of(2023, 2, 25),"Vehiculo de transporte"));
		listaRegistros.put(4, new Almacen("N20P24AREA1FIL23COL34",LocalDate.of(2022, 9, 10),"Puertas correderas"));
		listaRegistros.put(5, new Almacen("N11P22AREA1FIL23COL34",LocalDate.of(2020, 4, 24),"Servomotores"));
		listaRegistros.put(6, new Almacen("N09P11AREA1FIL23COL34",LocalDate.of(2019, 5, 22),"Torres de refrigeracion"));
		listaRegistros.put(7, new Almacen("N09P11AREA1FIL23COL34",LocalDate.of(2019, 5, 22),"Torres de refrigeracion"));
		
		System.out.println(listaRegistros.size());
		System.out.println(listaRegistros.get(3).getCodigo());
		System.out.println(listaRegistros.values());
		
		for(int i=1;i<listaRegistros.size();i++)
		{
			System.out.println(listaRegistros.get(i).getCodigo()+"\n");
		}
	}
	public void pruebaHashMap2(int conectados)
	{
		//USAR ESTE PARA UN SIMPLE HASHMAP
		String IP="";
		String NAME="";
		HashMap<String,String> UserIP= new HashMap<String,String>();
		
		//SE RELLENA EL HASHMAP
		for(int i=0; i<conectados;i++)
		{
			IP= JOptionPane.showInputDialog("Indique su IP con la que se ha conectado");
			NAME= JOptionPane.showInputDialog("Indique su nombre");
			UserIP.put(IP, NAME);
		}
		//EXPONEMOS CONTENIDO
		UserIP.forEach((ip,nombre)->
		 System.out.println("IP: " + ip + " → Nombre: " + nombre));
	}
	public void pruebaHasMap3(int conectados)
	{
		HashMap<String,Object> universoFicticio= new HashMap<>();
		universoFicticio.put("Ambur", new Planetario());
		universoFicticio.put("Noctis Ambrae", new Planetario());
		universoFicticio.put("Manpertos", new Planetario());
		
		HashMap<String,Object> universoReal= new HashMap<>();
		universoReal.put("La Tierra", new Planetario());
		universoReal.put("Marte", new Planetario());
		universoReal.put("Neptuno", new Planetario());
		
		HashMap<String,HashMap<String,Object>> multiverso= new HashMap<>();
		multiverso.put("UniversoFicticio", universoFicticio);
		multiverso.put("universoReal", universoReal);
	}
}

class Planetario
{
	private String nombre="";
	private int continentes=0;
	private double vida=0.0;
	
	public Planetario()
	{
		
	}
	
	public Planetario(String nombre,int continentes, double vida)
	{
		this.nombre=nombre;
		this.continentes=continentes;
		this.vida=vida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getContinentes() {
		return continentes;
	}

	public void setContinentes(int continentes) {
		this.continentes = continentes;
	}

	public double getVida() {
		return vida;
	}

	public void setVida(double vida) {
		this.vida = vida;
	}
	
}
