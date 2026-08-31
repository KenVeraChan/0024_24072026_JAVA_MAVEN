package conexionesJDBC;

import java.sql.*;

public class ConectaGestion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	try {
		//1 - CREAR CONEXION
		//En el caso de MYSQL
		Connection conector= DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebasdef","root","1234");
		
		//2 - CREAR EL STATENMENT
		Statement myst = conector.createStatement();
		
		//3 - EJECUTAR PETICION O CONSULTA SQL: se guardara una tabla virtual dentro de "myrs"
		ResultSet myrs= myst.executeQuery("SELECT * FROM productos");
		
		//4 - LEER EL ResultSet
		while(myrs.next())
		{
			//Devuelve los codigos de los articulos
			//No existe columna 0 en MYSQL se empieza siempre por la 1
			System.out.println(myrs.getString(1)+" "+myrs.getString(3));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}