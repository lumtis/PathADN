/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Adn.java
 * \brief Gestion d'un ADN
 */
package Adn;

import Univers.*;
import java.util.*;

public class Adn 
{	
	protected int genes = Constantes.nbrGenes; // nombre de gene de l'ADN
	protected List<Integer> listGenes = new ArrayList<Integer>(); //stocke les genes de l'ADN
	protected List<Points> listPoints = new ArrayList<Points>(); //stocke les déplacements de l'ADN
	protected int orientation = Constantes.Orientation; // 0 à 7 directions
	private int chemin = 0; // taille de son chemin pour arriver au point final
	
	
	
	/**
	 * Genere un ADN aléatoirement
	 * ses gènes corresponderont a son chemin
	 */
	public void generationAdn()
	{
		Random rd = new Random();
		int dir; // direction de l'adn
		for(int i = 0; i < genes ; i++)
		{
			dir = rd.nextInt(orientation);
			listGenes.add(dir);
		}
	}
	
	/**
	 * Effectue l'evaluation d'un chemin d'un adn
	 * Si l'adn ne trouve pas le chemin, une estimation est faite
	 * \param l'univers (le terrain)
	 * \param point de départ
	 * \return retourne le nombre de case effectué pour arriver au point final
	 */
	public int evalAdn(Terrain univers, Points pDepart)
	{
		
		Points pArrive = univers.posInfos[1]; // point d'arrivee
		Points pActuel = new Points(pDepart.getPosX(), pDepart.getPosY());
		Points ptmp = new Points(pDepart.getPosX(), pDepart.getPosY()); //au cas ou l'adn franchit un mur/depassement de terrain
		
		Points point; // sera enregistré dans la liste de points

		while( (pActuel.getPosX() != pArrive.getPosX() || pActuel.getPosY() != pArrive.getPosY()) && chemin < genes)
		{

			switch(getValAdn(chemin))
			{
				case 0: // HAUT
					pActuel.setPosY(pActuel.getPosY() - 1);
					break;
				case 1: // HAUT DROIT
					pActuel.setPos(pActuel.getPosX() + 1 , pActuel.getPosY() - 1);
					break;
				case 2: // DROIT
					pActuel.setPosX(pActuel.getPosX() + 1);
					break;
				case 3: // BAS DROIT
					pActuel.setPos(pActuel.getPosX() + 1 , pActuel.getPosY() + 1);
					break;
				case 4: // BAS
					pActuel.setPosY(pActuel.getPosY() + 1);
					break;
				case 5: // BAS GAUCHE
					pActuel.setPos(pActuel.getPosX() - 1 , pActuel.getPosY() + 1);
					break;
				case 6: // GAUCHE
					pActuel.setPosX(pActuel.getPosX() - 1);
					break;
				case 7: // HAUT GAUCHE
					pActuel.setPos(pActuel.getPosX() - 1 , pActuel.getPosY() - 1);
					break;
			}
			if(pActuel.getPosX() > (univers.getTailleX()-1) || pActuel.getPosY() > (univers.getTailleY() -1)
			   || pActuel.getPosX() < 0 || pActuel.getPosY() < 0 || univers.getPos(pActuel.getPosX(),pActuel.getPosY()) == Constantes.Obstacle) 
			{
				
				pActuel.setPos(ptmp.getPosX(), ptmp.getPosY()); // retour a l'ancienne position
			}
			else
				ptmp.setPos(pActuel.getPosX(), pActuel.getPosY());
	
						
			chemin++;	
			if(chemin == getNbrGenes())
			{
				/* a l'instant T l'adn n'a pas trouvé le chemin
				 * Il faut faire une estimation de la longueur
				 * Cette estimation correspond au nombre de case entre la position actuelle et le point d'arrivé
				 */
				 chemin += Math.abs(pActuel.getPosX() - pArrive.getPosX()) + Math.abs(pActuel.getPosY() - pArrive.getPosY());
			}
			// on enregistre les coordonnées de chaque points des déplacements
			point = new Points(pActuel.getPosX(), pActuel.getPosY());
			listPoints.add(point); 
				
		}
					
		return chemin;
	}
	
	//SETTERS
	public void setGene(int gene)
	{
		listGenes.add(gene);
	}
	public void setGeneIndex(int index,int gene)
	{
		listGenes.set(index,gene);
	}
	public void setNbrGenes(int max)
	{
		genes = max;
	}
	//GETTERS
	public int getNbrGenes()
	{
		return genes;
	}
	public int getValAdn(int index)
	{
		return listGenes.get(index);
	}
	public int getTailleChemin()
	{
		return chemin;
	}
	public Points getPoint(int index)
	{
		return listPoints.get(index);
	}
	public int getTaillePoints()
	{
		return listPoints.size();
	}

}
