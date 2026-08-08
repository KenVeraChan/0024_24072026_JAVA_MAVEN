package com.Rasselin.colecciones;

public class ProgramacionGenerica {

	public ProgramacionGenerica() {}
	
	public static <T> String estudiaArrays(T[]vector)
	{
		return "El vector tiene: "+vector.length+" elementos.";
	}
	public static <T extends Comparable> T devolverMinimo(T[]vector)
	{
		T objetoMenor=vector[0];
		if(vector[0].getClass().getName().toString().contains("String"))
		{  //Se obtiene el primer elemento, se obtiene su clase, se pasa a string legible y se busca si tiene STRING
			for(int i=1;i<vector.length;i++)
			{
				if(objetoMenor.compareTo(vector[i])>0)
				{
					objetoMenor=vector[i];
				}
			}
		}
		if(vector[0].getClass().getName().toString().contains("Double"))
		{
			for(int i=1;i<vector.length;i++)
			{
				if(objetoMenor.compareTo(vector[i])>0)
				{
					objetoMenor=vector[i];
				}
			}
		}
		
		System.out.println(vector[0].getClass().getName().toString());
		return objetoMenor;
	}
}
