/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 */
 
package Univers;
 
import Univers.*;
import Adn.*;
import Graphics.*;
import java.io.*;
public class ThreadMain extends Thread
{
	private Fenetre window;
	private Display disp;
	private Terrain t;
	
	public ThreadMain()
	{
	}
	
	/**
	 * Thread du programme
	 */
	public ThreadMain(Terrain u, Fenetre w, Display d)
	{
		t = u;
		window = w;
		disp = d;
	}
	public void run()
	{
	 	
		Population myPop = new Population(Constantes.Population);
		
		for(int generation = 0; generation < Constantes.Generation; generation ++)
		{
			for(Adn adn : myPop.getPop())
			{
				if(adn.getTaillePoints() == 0)
				{
					adn.evalAdn(t, t.posInfos[0]);
				}			
			}
			myPop.triPop();
			System.out.println("Generation: " + generation + " Meilleur chemin : " + myPop.getMeilleurAdn().getTailleChemin());
            myPop = Population.popEvolution(myPop, t);
            disp.setMeilleurAdn(myPop.getMeilleurAdn());
			window.dispTerrain(disp);

		} 	
		
	}
	
	/*
	 * public void run()
	{
		int generation;
		Population pop = new Population(Constantes.Population);
		Adn enfants[] = new Adn[Constantes.Population];
		
		int adnGenere;
		int nbAdnVivant = (int)Math.sqrt((float)Constantes.Population); // on garde tres peu de survivant pour un meilleur résultat
		int j,b = 0;
		
		for(generation = 0; generation < 50; generation ++)
		{
			j=0;
			adnGenere = 0;
			
			// Evaluation de la population
			for(Adn adn : pop.adns)
			{
				if(adn.getTaillePoints() == 0 || adn.getTailleChemin() == 0)
					adn.evalAdn(t,t.posInfos[0]);
			}
			// Tri selon leurs aptitude a résoudre la solution
			pop.triPop();
			
			// reproduction
			while(adnGenere < Constantes.Population)
			{
				enfants[adnGenere] = pop.adns.get(j);
				adnGenere++;
				for(int rep = j+1; rep < nbAdnVivant; rep++)
				{
					enfants[adnGenere] = pop.Reproduction(t, pop.adns.get(j), pop.adns.get(rep));
					adnGenere++;
					enfants[adnGenere] = pop.Reproduction(t, pop.adns.get(rep), pop.adns.get(j));
					adnGenere++;
				}
				j++;
			}			
			for(int i = 0; i < Constantes.Population; i++)
			{			
				pop.adns.set(i,enfants[i]);
			}	
			
			System.out.println("Generation ---- " + generation + " meilleur chemin : " + pop.getMeilleurAdn().getTailleChemin());
			disp.setMeilleurAdn(pop.getMeilleurAdn());
			window.dispTerrain(disp);
		}
		// On peut tout supprimer
		pop.Clear();
	} */ 
	
}
