/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Terrain.java
 * \brief Generation du terrain, obstacles
 */

package Univers;

import java.util.*;
import Univers.Points;
public class Terrain
{
		
	private int tailleX;
	private int tailleY;
	
	// L'univers est representé sous un tableau 2D
	private int[][] MapPos;
	private boolean solvable = false;
	// on stocke les coordonnées d'arrivé et de départ
	public static Points[] posInfos;
	
	private boolean init = false;
	
	
	public Terrain()
	{
	}
	/**
	 * Définit la taille de l'univers
	 * \param taille en X du terrain
	 * \param taille en Y du terrain
	 */
	public void setTailleTerrain(int x, int y)
	{
		this.tailleX = x;
		this.tailleY = y;
		
		posInfos = new Points[2]; // on stockera le point de départ et d'arrivé
		
		// On initialise notre terrain avec uniquement du sol
		MapPos = new int[this.tailleX][this.tailleY];
		for(int i = 0; i < this.tailleX ; i++){
			for(int j = 0; j < this.tailleY ; j++){
				MapPos[i][j] = Constantes.Sol;
			}
		}
	}
	/**
	 * Generation d'obstacle aléatoire sur le terrain
	 * \param nombre d'obstacle a générer
	 */
	public void genObstacle(int nbr)
	{
		// on génère X obstacle aléatoirement
		int posX, posY;
		Random rd = new Random();
		posX = rd.nextInt(this.tailleX);
		posY = rd.nextInt(this.tailleY);
		for(int i = 0; i < nbr ; i++)
		{
			while(MapPos[posX][posY] == Constantes.Obstacle)
			{
				posX = rd.nextInt(this.tailleX);
				posY = rd.nextInt(this.tailleY);
				
			}
			MapPos[posX][posY] = Constantes.Obstacle;
		}
		
	}	
	/**
	 * Generation du point de départ
	 */
	public void genDepart()
	{
		int posX, posY;
		Random rd = new Random();
		posX = rd.nextInt(this.tailleX);
		posY = rd.nextInt(this.tailleY);
		
		while(MapPos[posX][posY] == Constantes.Obstacle)
		{
			// Si jamais le point de départ est généré sur un obstacle...
			posX = rd.nextInt(this.tailleX);
			posY = rd.nextInt(this.tailleY);
		}
		MapPos[posX][posY] = Constantes.Adn;
		// on stocke les coordonnées du point de départ
		posInfos[0] = new Points(posX, posY);
		
	}
	/**
	 * Generation du point d'arrivé
	 */
	public void genArrive()
	{
		int posX, posY;
		Random rd = new Random();
		posX = rd.nextInt(this.tailleX);
		posY = rd.nextInt(this.tailleY);
		
		while(MapPos[posX][posY] == Constantes.Obstacle || MapPos[posX][posY] == Constantes.Adn)
		{
			// Si jamais le point de départ est généré sur un obstacle ou sur le point de départ
			posX = rd.nextInt(this.tailleX);
			posY = rd.nextInt(this.tailleY);
		}
		MapPos[posX][posY] = Constantes.Arrive;
		// on stocke les coordonnées du point de départ
		posInfos[1] = new Points(posX, posY);
	}
	/**
	 * Test de collision
	 */
	public boolean collision()
	{
		return (posInfos[0].getPosX() == 0 || posInfos[0].getPosX() == tailleX - 1) || 
			   (posInfos[0].getPosY() == 0 || posInfos[0].getPosY() == tailleY - 1) ;
	}
	/**
	 * Verifie si la solution est possible
	 **/
	public boolean solutionPossibe()
	{
			
		 int nbrObstacles = 0; 
		 
		 if(MapPos[posInfos[0].getPosX()][posInfos[0].getPosY() - 1] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX()][posInfos[0].getPosY() + 1] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() - 1][posInfos[0].getPosY()] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() + 1][posInfos[0].getPosY()] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() + 1][posInfos[0].getPosY() - 1] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() + 1][posInfos[0].getPosY() + 1] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() - 1][posInfos[0].getPosY() + 1] == Constantes.Obstacle)
			nbrObstacles++;
		 if(MapPos[posInfos[0].getPosX() - 1][posInfos[0].getPosY() - 1] == Constantes.Obstacle)
			nbrObstacles++;
		 
		 if(nbrObstacles == 8)
			return false;
		 return false;
	}
	
	//SETTERS
	public void setPos(int i, int j, int val)
	{
		this.MapPos[i][j] = val;
	}
	public void setInit(boolean b)
	{
		init = b;
	}
	public void Clear()
	{
		// On supprime l'univers
	}
	//GETTERS
	public int getTailleX()
	{
		return this.tailleX;
	}
	
	public int getTailleY()
	{
		return this.tailleY;
	}
	public int getPos(int i, int j)
	{
		return this.MapPos[i][j];
	}
	public boolean getInit()
	{
		return init;
	}
	public boolean isSolvable()
	{
		return solvable;
	}
}
