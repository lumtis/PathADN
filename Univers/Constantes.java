/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Constantes.java
 * \brief Constantes des differents attribut du programme
 */

package Univers;

public class Constantes{
	
	// Nom de la fenetre
	public static final String title = "Path ADN";
	// declaration des differentes sprites
	public static final String icone = "sprites/icone.png";
	public static final String spriteSol = "sprites/herbe.png";
	public static final String spriteObstacle = "sprites/mur.png";
	public static final String spriteAdn = "sprites/adn.png";
	public static final String spriteArrive = "sprites/arrive.png";
	public static final String spriteChemin = "sprites/chemin.png";
	
	// tailles des elements
	public static final int TAILLE_SPRITE = 32;
	public static final int TAILLE_TERRAIN_X = 640;
	public static final int TAILLE_TERRAIN_Y = 480;
	
	// Type
	public static final int Sol = 0; 
	public static final int Obstacle = 1;
	public static final int Adn = 2;
	public static final int Arrive = 3;
	public static final int Chemin = 4;
	
	//Adn
	public static final int nbrGenes = 100;
	public static final int Population = 100;
	public static final double taux = 0.5;
	public static final int adnTournois = 5;
	public static final int Orientation = 8;
	public static final int Generation = 800;
}
