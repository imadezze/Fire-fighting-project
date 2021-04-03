package simulation;

import robots.*;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

import gui.GUISimulator;

/**La classe qui stocke tous les données carte, incendies, robots */

public class DonneesSimulation{

    /** La carte avec tous les cases */
    private Carte carte;

    /** Une liste des incendies */
    private LinkedList<Incendie> incendies;

    /** Une liste des robots existantes*/
    private LinkedList<Robot> robots;

    /**Constructeur */
    /*public DonneesSimulation(Carte carte, LinkedList<Incendie> incendies, LinkedList<Robot> robots){
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
    }*/

    /** getter la carte */
    public Carte getCarte(){
        return carte;
    }

    /**setter une carte */
    public void setCarte(Carte cartee){
        carte = cartee;
    }

    /**setter des incendies */
    public void setIncendies(LinkedList<Incendie> des_incendies){
        incendies = des_incendies;
    }

    /**setter des robots */
    public void setRobots(LinkedList<Robot> des_robots){
        robots = des_robots;
    }

    /**gette la liste des incendies */
    public LinkedList<Incendie> getIncendie(){
        return incendies;
    }

    /**getter la liste des robots */
    public LinkedList<Robot> getRobot(){
        return robots;
    }

    /** changer la case de la carte par une case données*/
    public void setCase_donnees(int lig, int col, Case une_case){
        carte.setCase(lig, col, une_case);
    }

    /** ajouter une incendie à la liste des incendies */
    public void setIncendie(Incendie incendie){
        incendies.add(incendie);
    }

    /** ajouter un robot à la liste des robots */
    public void setRobot(Robot robot){
        robots.add(robot);
    }
    
    /** dessine les robots 
     * @throws DataFormatException */
    public void drawrobots(GUISimulator gui) throws DataFormatException {
    	int nbRobots = robots.size();
    	for (int i=0; i<nbRobots; i++) {
    		robots.get(i).draw(carte, gui);
    	}
    }
    
    /** dessine les incendie 
     * @throws DataFormatException */
    public void drawincendies(GUISimulator gui) throws DataFormatException {
    	
		int nbIncendies = incendies.size();
		for (int i=0; i<nbIncendies; i++) {
			incendies.get(i).draw(carte, gui);
		}
    }
}
