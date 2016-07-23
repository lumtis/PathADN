/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 */

import Univers.*;
import Adn.*;
import Graphics.*;
import java.io.*;

public class main 
{	
	public static void main(String[] args)
	{	
		
		//Creation du terrain
		Terrain t = new Terrain();
		Display disp = new Display();
		
		//Initialisation de l'univers
		Fenetre window = new Fenetre(t, disp);
		ThreadMain thAdn = new ThreadMain(t, window,disp);
		window.setThread(thAdn);
		

	}

}
