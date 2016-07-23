/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Population.java
 * \brief Traite les ADN d'une population + Reproduction
 */
package Adn;

import Univers.*;
import java.util.*;
import java.lang.*;

public class Population
{
	
	private List<Adn> adns = new ArrayList<Adn>(); //Liste d'adn
	private static List<Points> listRaccords = new ArrayList<Points>();
	private static List <Integer> listIndices = new ArrayList<Integer>(); // stocke la position du raccord

	
	/**
	 * Constructeur d'une population qui permet de créer un nombre d'ADN définie
	 * \param taille de la population
	 */
	public Population(int taille)
	{
		for(int i = 0; i < taille ; i++)
		{
			Adn newAdn = new Adn();
			newAdn.generationAdn(); // on créer un adn avec des genes aléatoire
			adns.add(newAdn);
		}
	}
	public Population()
	{

	}
	/**
	 * Fait évoluer une population
	 * \param la population a évoluer
	 * \param l'univers
	 * \return la nouvelle population
	 */
	public static Population popEvolution(Population pop, Terrain t) {
		
        Population newPopulation = new Population();
		
		// On garde le meilleur adn de la génération précedente
        newPopulation.adns.add(0, pop.getMeilleurAdn());

		// on reproduit notre population
        for (int i = 0; i < pop.getPop().size(); i++) 
        {
            Adn pere = tournoisAdn(pop,t);
            Adn mere = tournoisAdn(pop,t);
            Adn fils = Reproduction(t, pere, mere);
            newPopulation.adns.add(fils);
        }
        return newPopulation;
     }
	 /**
	 * Effectue un tournois entre X individus
	 * \param population
	 * \return un adn qui peut se reproduire
	 */
	 private static Adn tournoisAdn(Population pop, Terrain t) {
		 
        // Créer la population qui participera au tournois
        Population popTournois = new Population();
        // Pour chaque adn du tournois, on récupère un aléatoirement
        for (int i = 0; i < Constantes.adnTournois; i++) 
        {
            int randAdn = (int) (Math.random() * pop.getPop().size());
            popTournois.getPop().add(pop.getPop().get(randAdn));
        }
       
        // Recupere le meilleur adn
        Adn adn = popTournois.getMeilleurAdn();
        return adn;
    }
    /**
	 * Effectue la reproduction entre deux ADN
	 * \param l'univers (le terrain)
	 * \param adn du pere
	 * \param adn de la mere
	 * \return retourne l'adn du fils engendré par les parents
	 */
    private static Adn Reproduction(Terrain u, Adn pere, Adn mere)
	{
		 Adn fils = new Adn();
		 Random rand = new Random();
		 int genes = 0;

		 for(int i = 0; i < Math.min(pere.getTaillePoints(), mere.getTaillePoints()); i++)
		 {
			 if(Math.random() <= Constantes.taux)
				fils.setGene(pere.getValAdn(i));
			 else
				fils.setGene(mere.getValAdn(i));		
			genes++;	
					
		 }
		 /**
		  * Effectue une mutation sur le fils
		  * chaques gènes a 1% de chance de muter
		  */
		 Random rd = new Random();
		 for(int i = 0; i < genes ; i++)
		 {
			int val = rd.nextInt(Constantes.nbrGenes + 1); // 0.01% de chance de mutation
			if(val == 100)
			{
				int newGene = (fils.getValAdn(i)+val) % Constantes.Orientation;
				fils.setGeneIndex(i,newGene);
			}
		 }
		 fils.setNbrGenes(genes);
		 
		 return fils;
	 }
	/**
	 * Effectue la reproduction entre deux ADN
	 * \param l'univers (le terrain)
	 * \param adn du pere
	 * \param adn de la mere
	 * \return retourne l'adn du fils engendré par les parents
	 */
	private static Adn Reproduction_old(Terrain u, Adn pere, Adn mere)
	{
		Adn fils = new Adn();
		int ind = 0;
		int nbrGenes = 0;
		// On va rechercher les raccords
		for(int i = 0; i < Math.min(pere.getTaillePoints(), mere.getTaillePoints()); i++)
		{
			// s'il y en a plusieurs, on devra choisir le meilleur point
			if(pere.getPoint(i).egal(mere.getPoint(i)) && i > 0)
			{
				listRaccords.add(pere.getPoint(i));
				listIndices.add(i);
			}
		}
		// On va selectionner le meilleur raccord par rapport a la mère
		triRaccord(u, mere);
		
		// recuperation de l'indice ou l'on doit effectuer le mélange entre les genes du pere et de la mere
		if(listRaccords.size() != 0)
		{
			ind = listIndices.get(0);
			//le fils aura les genes du pere avant le point de raccord
			for(int i = 0; i <= ind; i++)
			{
				fils.setGene(pere.getValAdn(i));
				nbrGenes++;
			}
			for(int i = ind + 1; i < mere.getTaillePoints(); i++)
			{
				fils.setGene(mere.getValAdn(i));
				nbrGenes++;				
			}		
		}
		else
		{
			// Aucun point de raccord, on fait un mix des parents
			for(int i = 0; i < Math.min(pere.getTaillePoints(), mere.getTaillePoints()); i++)
			 {
				 if(Math.random() <= Constantes.taux)
					fils.setGene(pere.getValAdn(i));
				 else
					fils.setGene(mere.getValAdn(i));		
				nbrGenes++;	
						
			 }	
		}
		fils.setNbrGenes(nbrGenes);
		//Mutation
		Random rd = new Random();
		for(int i = 0; i < fils.getTaillePoints(); i++)
		{
			int val = rd.nextInt(Constantes.nbrGenes); // 0.01% de chance de mutation
			if(val == 100)
			{
				int newGene = (fils.getValAdn(i)+val) % Constantes.Orientation;
				fils.setGeneIndex(i,newGene);
			}
		}

		// on remet tout à 0
		listRaccords.clear();
		listIndices.clear();
		return fils;
	}
	/**
	 * Trie la liste de population selon la longueur de leurs chemins
	 */
	 public void triPop()
	 {
		boolean nbPerm = true;
		Adn adn;
		while(nbPerm)
		{
			nbPerm = false;
			for(int i = 0; i < adns.size() -1 ; i++)
			{
				// si on trouve un meilleur chemin, on le place en position i-1
				if(getAdn(i).getTailleChemin() > getAdn(i+1).getTailleChemin()){
					adn = adns.get(i);
					adns.set(i,getAdn(i+1));
					adns.set(i+1, adn);
					nbPerm = true;
				}
			 }
		 }
	 }
	/**
	 * Trie la liste des raccords pour en selectionner le meilleur
	 * \param l'univers
	 * \param l'adn de la mere
	 */
	private static void triRaccord(Terrain u, Adn Mere)
	{
		boolean nbPerm = true;
		Points pTmp;
		while(nbPerm)
		{
			nbPerm = false;
			for(int i = 0; i < listRaccords.size() -1 ; i++)
			{
				if(Mere.evalAdn(u, listRaccords.get(i)) > Mere.evalAdn(u, listRaccords.get(i+1)))
				{
					pTmp = listRaccords.get(i);
					listRaccords.set(i, listRaccords.get(i+1));
					listRaccords.set(i+1, pTmp);
					nbPerm = true;
				}
			 }
		 }
	}
	public void Clear()
	{
		adns.clear();
		listRaccords.clear();
		listIndices.clear();
	}
	public void dispRaccord()
	{
		for(Points p : listRaccords)
			System.out.println(p);

	}
	//Getters
	public Adn getAdn(int index)
	{
		return adns.get(index);
	}
	
	public Adn getMeilleurAdn()
	{
		return adns.get(0);
	}
	public List<Adn> getPop()
	{
		return adns;
	}
}
