package com.Rasselin.colecciones;

public class BancoColecciones {

	private String nombreUsuario="";
	private String numCuenta="";
	private double saldo=0.0;
	
	public BancoColecciones(String nombreusuario,String numCuenta,double saldo)
	{
		this.nombreUsuario=nombreUsuario;
		this.numCuenta=numCuenta;
		this.saldo=saldo;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}

class Libros
{
	private String autor="";
	private String titulo="";
	private String ISBN="";
	
	public Libros(String autor,String titulo,String ISBN)
	{
		this.autor=autor;
		this.titulo=titulo;
		this.ISBN=ISBN;
	}

	public String registro()
	{
		return "El autor: "+this.autor+" publicó: "+this.titulo+" con ISBN: "+this.ISBN;
	}
	
	public boolean equals(Object o)
	{
		Libros otroLibro= (Libros)o;
		if(o instanceof Libros)
		{
			Object ISBNlocal=this.ISBN;
			if(ISBNlocal==otroLibro.ISBN)
			{
				return true;
			}
			else
			{
				return false;	
			}
		}
		else
		{
			return false;
		}
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
