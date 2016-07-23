/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Display.java
 * \brief Gestion de l'affichage
 */

package Graphics;

import Univers.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import Adn.*;

public class Display extends JPanel
{
	
	private Terrain univers; // l'univers
	private Adn adn; // meilleur chemin a afficher
	private Image img; // image a afficher
	
	// Sprite de l'univers
	private Image img_sol;
	private Image img_obs;
	private Image img_adn;
	private Image img_arrive;
	private Image img_chemin;
	
	
	private Points pActuel, pPrecedent;
	
	/**
	 * Charge les differentes images a afficher sur le terrain
	 */
	public void ChargementSprites()
	{
		try
		{
			img_sol = ImageIO.read(new File(Constantes.spriteSol));
			img_obs = ImageIO.read(new File(Constantes.spriteObstacle));
			img_adn = ImageIO.read(new File(Constantes.spriteAdn));
			img_arrive = ImageIO.read(new File(Constantes.spriteArrive));
			img_chemin = ImageIO.read(new File(Constantes.spriteChemin));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Affiche notre univers en temps réel
	 */
	public void paintComponent(Graphics g)
	{	
		// L'univers est-il initialisé?
		if(univers.getInit())
		{
			super.paintComponent(g);
			afficheTerrain(g);
			
			// Attribut pour le marqueur de chemin
			Graphics2D g2d = (Graphics2D)g;
			g.setColor(Color.red);
			g2d.setStroke(new BasicStroke(3));
			
			for(int i = 0 ; i < adn.getTaillePoints() ; i++)
			{
				if(i == 0)
				{
					pActuel = new Points(adn.getPoint(i));
					pPrecedent = new Points(univers.posInfos[0]);	
					g.drawLine(pPrecedent.getPosX() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pPrecedent.getPosY() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pActuel.getPosX() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pActuel.getPosY() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2));

				}
				else
				{			
					pActuel = new Points(adn.getPoint(i));
					pPrecedent = new Points(adn.getPoint(i-1));	
					g.drawLine(pPrecedent.getPosX() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pPrecedent.getPosY() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pActuel.getPosX() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2), pActuel.getPosY() * Constantes.TAILLE_SPRITE + (Constantes.TAILLE_SPRITE / 2));
				}
			}
		}
	}
	/**
	 * Affiche les elements de l'univers
	 * Point de départ / point d'arrivé / obstacle 
	 * \param graphic pour dessiner les different elements
	 */
	public void afficheTerrain(Graphics g)
	{
		for(int i = 0 ; i < univers.getTailleX(); i++)
		{
			for(int j = 0; j < univers.getTailleY(); j++)
			{
				switch(univers.getPos(i,j))
				{
					case Constantes.Sol:
						img = img_sol;
						break;
					case Constantes.Obstacle:
						img = img_obs;
						break;
					case Constantes.Adn:
						img = img_adn;
						break;
					case Constantes.Arrive:
						img = img_arrive;
						break;
				}
				g.drawImage(img, i * Constantes.TAILLE_SPRITE, j * Constantes.TAILLE_SPRITE, this);
			}
		}
	}
	//SETTERS
	public void setUnivers(Terrain t)
	{
		this.univers = t;
	}
	//SETTERS
	public void setMeilleurAdn(Adn a)
	{
		this.adn = a;
	}
	
	
} 


