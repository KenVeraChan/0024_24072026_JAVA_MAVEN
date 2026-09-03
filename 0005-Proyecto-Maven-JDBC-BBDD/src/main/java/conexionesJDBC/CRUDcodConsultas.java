package conexionesJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDcodConsultas {


	public CRUDcodConsultas()
	{
		try {
			//1 - CREAR CONEXION
			//En el caso de MYSQL
			Connection conector= DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebasdef","root","1234");
			
			//2 - CREAR EL STATENMENT
			Statement myst = conector.createStatement();
			
			//3 - EJECUTAR PETICION O CONSULTA SQL: se guardara una tabla virtual dentro de "myrs"
			ResultSet myrs= myst.executeQuery("SELECT * FROM imagenesinterfazweb");
			
			//4 - LEER EL ResultSet
			while(myrs.next())
			{
				//Devuelve los codigos de los articulos
				//Como los productos están agrupados por ID (clave) para apuntar a un objeto (VALOR) se usara HASHMAP
				
				System.out.println(myrs.getString(1)+" "+myrs.getString(3));
			}
			//Si se ha terminado la operación se cierra todo como buena practica
			myrs.close();  //Liberar los recursos que se usaban en memoria
			conector.close();  //Liberar el conector que se establecio
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ObjetoVenta
{
	private String nombre;     //Nombre del producto-servicio-proyecto
	private String destino;    //Clasificacion: PRODUCTO, SERVICIO, PROYECTO
	private String sector;     //Subclasificacion dentro de: PRODUCTO, SERVICIO, PROYECTO
	private int cantidad;      //Numero de unidades existentes en el STOCK
	private double coste;      //Coste unitario decimal
	private String detalles;   //Breve descripcion del articulo de venta
	
	public ObjetoVenta()
	{
		
	}
}
