/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Terrain.java
 * \brief Gestion du point de départ et d'arrivé
 */

package Univers;

public class Points 
{

	private int posX;
	private int posY;
	
	public Points()
	{
	}
	/**
	 * Constructeur de point
	 * \param un point
	 */
	public Points (Points p)
	{
		this.posX = p.posX;
		this.posY = p.posY;
	}
	/**
	 * Constructeur de point
	 * \param coordonnees en X du point
	 * \param coordonnees en Y du point
	 */
	public Points(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * Test l'egalité entre deux points
	 * \param un point a verifier
	 * \return renvoie vrai si les points sont égaux
	 */
	public boolean egal(Points p)
	{
		if(this.posX == p.posX && this.posY == p.posY)
			return true;
		else
			return false;
	}
	
	//GETTERS
	public int getPosX()
	{
		return this.posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
	
	//SETTERS
	public void setPos(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	public void setPosX(int x)
	{
		this.posX = x;
	}
	public void setPosY(int y)
	{
		this.posY = y;
	}

	public String toString()
	{
		return "(" + this.posX + ";" + this.posY +")";
	}
}
