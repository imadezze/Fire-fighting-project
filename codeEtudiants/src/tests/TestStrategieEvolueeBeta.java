package tests;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import evenement.*;
import Strategie.*;
import io.LecteurDonnees;
import robots.*;
import simulation.*;
import gui.GUISimulator;
public class TestStrategieEvolueeBeta {
    /**
     * Fonction de test, pour eteindre les incendies
     * @param args
     */
    public static void main(String[] args){
        try{
            StrategieEvoluee strategie = new StrategieEvoluee();
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            //HashMap<Incendie, Robot> mapincendierobot = new HashMap<Incendie, Robot>();
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
            //datesuivante = strategie.eteinte(date, donnees, inc, robot, min);
            
            int indiceIncendie = 0;
            HashMap<Robot, Long> mapdaterobot = new HashMap<Robot, Long>();
            for (Robot r: donnees.getRobot()){
                mapdaterobot.put(r,(long) 0);
            }
            for (Incendie incendie : donnees.getIncendie()) {
                long minDate = Integer.MAX_VALUE;
                Robot robot = null;
                for (Robot r : mapdaterobot.keySet()){
                    if ((mapdaterobot.get(r) < minDate) && (r.getVitesse(incendie.getLacase().getNature()) != 0)){
                        robot = r;
                        minDate = mapdaterobot.get(r);
                    }
                }
                robot.setSimul(simul);
                boolean remplir = false;
                if ((minDate != 0) && !(robot instanceof RobotPatte)){
                    remplir = true;                    
                }
                long datesuivante2 = strategie.eteinte(minDate, donnees, incendie, robot, min, remplir, robot.getPositionFictive());
                mapdaterobot.replace(robot, datesuivante2);
            }
           
            System.out.println();
            System.out.println("ça peut prendre du temps quand même ;)");
            System.out.println("Pas un problème, c'est la durée normale de la simulation");
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}