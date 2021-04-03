package tests;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import io.*;
import simulation.*;
import robots.*;
import evenement.*;
import dijkstra.*;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import gui.ImageElement;

public class TestScenario {
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        
        try {
        	
        	DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
        	
		    Carte cart = donnees.getCarte();
		    Robot dron = donnees.getRobot().get(0);
		    int ligne = dron.getPosition().getLigne();
		    int colonne = dron.getPosition().getColonne();

		    int heigth = 800;
		    int width = 1000;
		    
		    int min = heigth/cart.getNblignes();
		    if (min > width/cart.getNbColonnes()) {min = width/cart.getNbColonnes();}
		    
		    EvenDeplacementRobot e1 = new EvenDeplacementRobot((long) 1, ligne -1 , colonne, dron, cart, min);
		    EvenDeplacementRobot e2 = new EvenDeplacementRobot((long) 20, ligne -2 , colonne, dron, cart, min);
		    EvenDeplacementRobot e3 = new EvenDeplacementRobot((long) 30, ligne -3 , colonne, dron, cart, min);
		    EvenDeplacementRobot e4 = new EvenDeplacementRobot((long) 40, ligne -4 , colonne, dron, cart, min);
		    
		    Simulateur simul = new Simulateur(0, donnees);
		    simul.ajouteEvenement(e1);
		    simul.ajouteEvenement(e2);
		    simul.ajouteEvenement(e3);
		    simul.ajouteEvenement(e4);
		    
		    GUISimulator gui = new GUISimulator(width, heigth, Color.BLACK, simul);
		    simul.setGui(gui);
		    
	    	donnees.getCarte().drawCarte(donnees, gui);
		    donnees.drawrobots(gui);
		    donnees.drawincendies(gui);
		    dron.setSimul(simul);
		    
		    System.out.println();
		    System.out.println("Spoiler alert: le robot ne sort pas de la carte, une exception est levée");
		    
		    
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
	}
}
