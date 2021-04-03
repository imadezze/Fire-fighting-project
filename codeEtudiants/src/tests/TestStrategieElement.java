package tests;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import evenement.*;
import Strategie.StrategieElement;
import io.LecteurDonnees;
import robots.*;
import simulation.*;
import gui.GUISimulator;
public class TestStrategieElement {
    /**
     * Fonction de test, pour eteindre les incendies
     * @param args
     */
    public static void main(String[] args){
        try{
            StrategieElement strategie = new StrategieElement();
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            Simulateur simul = new Simulateur(0, donnees);
            Carte cart = donnees.getCarte();
		    int heigth = 800;
		    int width = 1000;
            GUISimulator gui = new GUISimulator(width, heigth, Color.BLACK, simul);
            simul.setGui(gui);
            donnees.getCarte().drawCarte(donnees, gui);
		    donnees.drawrobots(gui);
            donnees.drawincendies(gui);
		    int min = heigth/cart.getNblignes();
            if (min > width/cart.getNbColonnes()) {min = width/cart.getNbColonnes();}
            long date = 0;
            long datesuivante = date;
            //Incendie inc = donnees.getIncendie().get(0);
            //Robot robot = donnees.getRobot().get(0);
            //robot.setSimul(simul);
            //datesuivante = strategie.eteinte(date, donnees, inc, robot, min, robot.getPositionFictive());
            
            int indiceIncendie = 0;
            HashMap<Robot, Long> mapdaterobot = new HashMap<Robot, Long>();
            for (Robot r: donnees.getRobot()){
                mapdaterobot.put(r,(long) 0);
            }
            for (Incendie incendie : donnees.getIncendie()) {
                long minDate = Integer.MAX_VALUE;
                Robot robot = null;
                for (Robot r : mapdaterobot.keySet()){
                    if ((mapdaterobot.get(r) < minDate) && (r.getVitesse(incendie.getLacase().getNature()) != 0) && r.getEtat() == false){
                        robot = r;
                        minDate = mapdaterobot.get(r);
                    }
                }
                if (robot != null){
                    robot.setSimul(simul);
                    long datesuivante2 = strategie.eteinte(minDate, donnees, incendie, robot, min, robot.getPositionFictive());
                    mapdaterobot.replace(robot, datesuivante2);
                }
                
            }
            
        System.out.println("Spoiler Alert: la simulation s'arrête sans éteindre toutes les incendies: ce qu'est le but de cette stratégie élémentaire");
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}