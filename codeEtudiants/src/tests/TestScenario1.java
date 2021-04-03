package tests;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import evenement.EvenDeplacementRobot;
import evenement.EvenRemplirReservoir;
import evenement.EvenVerserleau;
import evenement.EvenVerserleauFin;
import evenement.Evenement;
import evenement.Simulateur;
import gui.GUISimulator;
import io.LecteurDonnees;
import robots.Robot;
import simulation.Carte;
import simulation.DonneesSimulation;
import simulation.Incendie;

public class TestScenario1 {
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        
        try {
        	
        	DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
        	
		    Carte cart = donnees.getCarte();
		    Robot robotroue = donnees.getRobot().get(1);
		    Incendie incendie = donnees.getIncendie().get(4);
		    int ligne = robotroue.getPosition().getLigne();
		    int colonne = robotroue.getPosition().getColonne();

		    int heigth = 800;
		    int width = 1000;
		    
		    int min = heigth/cart.getNblignes();
		    if (min > width/cart.getNbColonnes()) {min = width = 1000/cart.getNbColonnes();}
		    
		    Simulateur simul = new Simulateur(0, donnees);
		    int intensite = incendie.getNbrLitres();
		    EvenDeplacementRobot e1 = new EvenDeplacementRobot((long) 1, ligne -1 , colonne, robotroue, cart, min);
		    EvenVerserleau ee1 = new EvenVerserleau((long)2, incendie, robotroue, donnees, intensite); 
		    intensite -= robotroue.getQuantiteEau();
		    EvenDeplacementRobot e2 = new EvenDeplacementRobot((long) 20, ligne -1 , colonne-1, robotroue, cart, min);
		    EvenDeplacementRobot e3 = new EvenDeplacementRobot((long) 30, ligne -1 , colonne-2, robotroue, cart, min);
		    EvenRemplirReservoir ee2 = new EvenRemplirReservoir((long)31, robotroue, cart);
		    EvenDeplacementRobot e4 = new EvenDeplacementRobot((long) 40, ligne -1 , colonne-1, robotroue, cart, min);
		    EvenDeplacementRobot e5 = new EvenDeplacementRobot((long) 50, ligne -1 , colonne, robotroue, cart, min);
		    EvenVerserleau ee3 = new EvenVerserleau((long)51, incendie, robotroue, donnees, intensite);
		    EvenVerserleauFin ee4 = new EvenVerserleauFin((long) 55, incendie, robotroue, donnees);
		    
		    simul.ajouteEvenement(e1);
		    simul.ajouteEvenement(ee1);
		    simul.ajouteEvenement(e2);
		    simul.ajouteEvenement(e3);
		    simul.ajouteEvenement(ee2);
		    simul.ajouteEvenement(e4);
		    simul.ajouteEvenement(e5);
		    simul.ajouteEvenement(ee3);
		    simul.ajouteEvenement(ee4);
		    
		    GUISimulator gui = new GUISimulator(width, heigth, Color.BLACK, simul);
		    simul.setGui(gui);
		    // zone de test
		 
	    	donnees.getCarte().drawCarte(donnees, gui);
		    donnees.drawrobots(gui);
		    donnees.drawincendies(gui);
		    
		    System.out.println();
		    System.out.println("le robot à roue éteind l'incendie numéro 4");
	    
		    
		        
		    
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
	}
}
