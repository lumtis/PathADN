/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Constantes.java
 * \brief Classe regroupant des fonctions utiles
 */
package Univers;

public class Utils
{
	public static boolean isInt(String val) 
	{
		boolean v = false;
		try {
			//test de convesion
			Integer.parseInt(val);
			v = true;
		} catch (Exception e) {
			//conversion échouée
			v = false;
		}
		 
		return v;
	}
}
