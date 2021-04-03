package tests;

import io.*;
import simulation.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import gui.ImageElement;

public class TestSimulateur {
	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
       
		
		try {
	        GUISimulator gui = new GUISimulator(1000, 800, Color.BLACK);

			 DonneesSimulation donnees =  LecteurDonnees.creeDonnees(args[0]);
		     donnees.getCarte().drawCarte(donnees, gui);
		     donnees.drawrobots(gui);
		     donnees.drawincendies(gui);

		     System.out.println();
		     System.out.println("Ce test a pour but d'afficher la carte et rien d'autre :)");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
