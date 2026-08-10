package com.Rasselin.colecciones4;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Mapas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,Almacen> listaRegistros= new HashMap<String,Almacen>();
		listaRegistros.put("0010", new Almacen("N12P20AREA1FIL23COL34",LocalDate.of(2026, 12, 5),"Teclados ordenador"));
		listaRegistros.put("0013", new Almacen("N01P22AREA2FIL25COL40",LocalDate.of(2025, 11, 15),"Figrorifico industrial"));
		listaRegistros.put("0035", new Almacen("N03P22AREA1FIL23COL34",LocalDate.of(2023, 2, 25),"Vehiculo de transporte"));
		listaRegistros.put("0042", new Almacen("N20P24AREA1FIL23COL34",LocalDate.of(2022, 9, 10),"Puertas correderas"));
		listaRegistros.put("0208", new Almacen("N11P22AREA1FIL23COL34",LocalDate.of(2020, 4, 24),"Servomotores"));
		listaRegistros.put("0340", new Almacen("N09P11AREA1FIL23COL34",LocalDate.of(2019, 5, 22),"Torres de refrigeracion"));
		listaRegistros.put("0341", new Almacen("N09P11AREA1FIL23COL34",LocalDate.of(2019, 5, 22),"Torres de refrigeracion"));

		System.out.println(listaRegistros.size());

		System.out.println(listaRegistros.get("0010").getProductos());
		
		
		for(Map.Entry<String, Almacen> listado: listaRegistros.entrySet())
		{
			String producto=listado.getKey();
			Almacen valor=listado.getValue();
			System.out.println("Clave: "+producto+" y Valor: {Producto: "+valor.getProductos()+", Fecha registro: "+valor.getFecha()+", Localizacion almacenaje: "+valor.getId()+"}");
		}

		System.out.println("");
		System.out.println("ORDENADO POR CLAVE");
		System.out.println("");
		
		//ORDENADO POR CLAVES: Al convertirse en TreeMap ya se ordena solo por claves directamente
			Map<String, Almacen> ordenadoPorClave = new TreeMap<>(listaRegistros);
			for(Map.Entry<String, Almacen> listado: ordenadoPorClave.entrySet())
			{
				String producto=listado.getKey();
				Almacen valor=listado.getValue();
				System.out.println("Clave: "+producto+" y Valor: {Producto: "+valor.getProductos()+", Fecha registro: "+valor.getFecha()+", Localizacion almacenaje: "+valor.getId()+"}");
			}
		System.out.println("");
		System.out.println("ORDENADO POR VALOR: Nombre Producto");
		System.out.println("");
		
		//ORDENADO POR VALORES: Nombre del producto
			List<Map.Entry<String, Almacen>> entradasOrdenadas = listaRegistros.entrySet()
				    .stream()
				    .sorted(Map.Entry.comparingByValue(
				            Comparator.comparing(Almacen::getProductos, String.CASE_INSENSITIVE_ORDER)
				        ))
				        .collect(Collectors.toList());
	
			// Recorrer el resultado ordenado
			for (Map.Entry<String, Almacen> entrada : entradasOrdenadas) {
			    System.out.println("Clave: " + entrada.getKey() + " | Almacén: " + entrada.getValue().getId()+ " | Producto: " + entrada.getValue().getProductos());
			}
		
		System.out.println("");
		System.out.println("ORDENADO POR VALOR: Fecha registro Producto");
		System.out.println("");
		//ORDENADO POR VALORES: Nombre del producto
			List<Map.Entry<String, Almacen>> entradasOrdenadasFecha = listaRegistros.entrySet()
				    .stream()
				    .sorted(Map.Entry.comparingByValue(
				            Comparator.comparing(Almacen::getFecha)
				        ))
				        .collect(Collectors.toList());
	
			// Recorrer el resultado ordenado
			for (Map.Entry<String, Almacen> entrada : entradasOrdenadasFecha) {
			    System.out.println("Clave: " + entrada.getKey() + " | Almacén: " + entrada.getValue().getId()+ " | Producto: " + entrada.getValue().getProductos()+ " | Fecha registro: " + entrada.getValue().getFecha());
			}

	}
	
}
class Almacen
{
	private String id="";
	private LocalDate fecha;
	private String producto;
	
	public Almacen(String id, LocalDate fecha,String producto)
	{
		this.id=id;
		this.fecha=fecha;
		this.producto=producto;
	}
	public String getId()
	{
		return this.id;
	}
	public LocalDate getFecha()
	{
		return this.fecha;
	}
	public String getProductos()
	{
		return this.producto;
	}
}