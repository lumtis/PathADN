/**
 * Projet POO
 * \author ABDESSELAM Gaia
 * \author BERTRAND Lucas
 * \file Fenetre.java
 * \brief Gestion de la fenetre
 */

package Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.awt.*;
import Univers.*;
import Adn.*;

public class Fenetre extends JFrame implements ActionListener
{
	/**
	 * Déclarations des différents élements de la fenêtre 
	 */
	private int tailleMenu = 300 ; 
	private int tailleX = Constantes.TAILLE_TERRAIN_X + tailleMenu;
	private JLabel lb_case_x,lb_case_y, lb_obs;
	private JPanel panel = new JPanel();
	private JTextField jtf1, jtf2, jtf3;
	private JButton bt_generer;
	private JOptionPane jop1;
	private ThreadMain thAdn;
	private Terrain univers;
	private Display disp;
 	
	/**
	 * Initialise de la partie graphique
	 */	
	public Fenetre(Terrain t, Display disp)
	{
		this.setTitle(Constantes.title);
		this.setSize(tailleX, Constantes.TAILLE_TERRAIN_Y);
		this.setLocationRelativeTo(null); // centre la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // permet de quitter l'application       
		this.setResizable(false); // ne pas redimensionner la fenetre en cours d'execution
		this.setIconImage(new ImageIcon(Constantes.icone).getImage()); // icone
		
		initComposants();
		disp.setLayout(null);
		panel.setLayout(null);
		panel.setBounds(tailleX - (tailleMenu),0,tailleMenu,130); // jpanel univers
		disp.add(panel);
		bt_generer.addActionListener(this);
		
		// affiche notre fenetre
		this.setContentPane(disp);
		
		this.setVisible(true);
	
		univers = t;
		this.disp = disp;
		disp.setUnivers(univers); 
		disp.ChargementSprites(); 
					
	}
	private void initComposants()
	{
		lb_case_x = new JLabel("Nombre de cases en X :");
		lb_case_y = new JLabel("Nombre de cases en Y :");
		lb_obs = new JLabel("Nombre d'obstacles :");
		bt_generer = new JButton("Générer");
		
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		jtf3 = new JTextField();
		
		panel.setBorder(BorderFactory.createTitledBorder("Univers"));
		lb_case_x.setBounds(10,-10,200,100);
		lb_case_y.setBounds(lb_case_x.getX(),lb_case_x.getY() + 20,200,100);
		lb_obs.setBounds(lb_case_y.getX(),lb_case_y.getY()+ 20,200,100);
		
		jtf1.setBounds(lb_case_x.getX()+ 170, lb_case_x.getY() + 40,30,20);
		jtf2.setBounds(lb_case_x.getX()+ 170, jtf1.getY() + 20,30,20);
		jtf3.setBounds(lb_case_x.getX()+ 170, jtf2.getY() + 20 ,30,20);
		bt_generer.setBounds(lb_case_x.getX() + 80, jtf3.getY() + 30 ,100,25);
		
		panel.add(lb_case_x);
		panel.add(lb_case_y);
		panel.add(lb_obs);
		
		panel.add(jtf1);
		panel.add(jtf2);
		panel.add(jtf3);
		
		panel.add(bt_generer);
		
		// Popup d'erreur
		jop1 = new JOptionPane();    
		  

	}  
	/**
	 * Affichage de l'univers et ses composantes toutes les X secondes
	 */
	public void dispTerrain(Display disp)
	{
		disp.repaint();
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Cette fonction est lancée lors du click de la souris
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		/**
		 * Vérification des champs
		 * S'ils sont tous renseignés, des entiers
		 * plage de valeurs valides ?
		 */
		 if(Utils.isInt(jtf1.getText()) && Utils.isInt(jtf2.getText()) && Utils.isInt(jtf3.getText()))
		 {
			 int case_X = Integer.parseInt(jtf1.getText());
			 int case_Y = Integer.parseInt(jtf2.getText());
			 int nbr_Obstacles = Integer.parseInt(jtf3.getText());
			 if(case_X > 0 && case_Y > 0 && nbr_Obstacles >= 0 )
			 {
				 /**
				  * Vérification si les plages sont valides
				  */
				  int nbr_case_x = Constantes.TAILLE_TERRAIN_X / Constantes.TAILLE_SPRITE;
				  int nbr_case_y = (Constantes.TAILLE_TERRAIN_Y / Constantes.TAILLE_SPRITE) - 1;
				  int max_obstacles = nbr_case_x * nbr_case_y -1;
				  if(case_X <= nbr_case_x && case_Y <= nbr_case_y && nbr_Obstacles < max_obstacles)
				  {
					  /**
					   * Tout est correct, on peut générer les obstacles, points d'arrivé/départ
					   */
					if(thAdn.getState() == Thread.State.NEW)
					{						
						thAdn.start(); // Si c'est la premiere fois 
					}
					else if(thAdn.getState() == Thread.State.TERMINATED)
					{	
						// On demande un nouvel univers
						univers.Clear();
						thAdn = new ThreadMain(univers, this, disp);
						thAdn.start();
					}
				    if(thAdn.getState() == Thread.State.TIMED_WAITING)
						jop1.showMessageDialog(null, "Un chemin est en cours de résolution !", "Erreur", JOptionPane.ERROR_MESSAGE, null);
					else
					{
						univers.setTailleTerrain(case_X,case_Y);
						univers.genObstacle(nbr_Obstacles);
						univers.genDepart();
						univers.genArrive();				
						univers.setInit(true);
					}
				  }
				  else
					jop1.showMessageDialog(null, "Le nombre de case est trop grand", "Erreur", JOptionPane.ERROR_MESSAGE, null);
			 }
			 else
				jop1.showMessageDialog(null, "Les nombres doivent être positifs !", "Erreur", JOptionPane.ERROR_MESSAGE, null);
		 }
		 else
			jop1.showMessageDialog(null, "Vous n'avez pas correctement renseigné les champs !", "Erreur", JOptionPane.ERROR_MESSAGE, null);  
	} 
	
	public void setThread(ThreadMain t)
	{
		thAdn = t;
	}
}
