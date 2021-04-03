package tests;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import evenement.*;
import Strategie.*;
import io.LecteurDonnees;
import robots.*;
import simulation.*;
import gui.GUISimulator;

public class TestStrategieEvoluee {
	public static void main(String[] args) {
		try{
            StrategieEvoluee strategie = new StrategieEvoluee();
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
            /*Ici on ne traite qu'un seul incendie*/
            //long date = 0;
            //long datesuivante = date;
            //Incendie inc = donnees.getIncendie().get(0);
            //Robot robot = donnees.getRobot().get(0);
            //robot.setSimul(simul);
            //datesuivante = strategie.eteinte(date, donnees, inc, robot, min);
            
            /*ArrayList<Incendie> SortedFires= new ArrayList<Incendie>();
            for (int i = 0; i < donnees.getIncendie().size(); i++) {
            	int max = i;
            	for (int j = 0; j < donnees.getIncendie().size(); j++) {
            		if (donnees.getIncendie().get(i).getNbrLitres() < donnees.getIncendie().get(j).getNbrLitres()) {
            			max = j;
            		}
            	}
            	SortedFires.add(donnees.getIncendie().get(max));
            }*/
            
            HashMap<Robot, Long> mapdaterobot = new HashMap<Robot, Long>();
            for (Robot robot: donnees.getRobot()){
            	if (robot instanceof Drone){
                    ((Drone) robot).setGraphe(donnees.getCarte());
                }
                else if (robot instanceof RobotChenille){
                    ((RobotChenille) robot).setGraphe(donnees.getCarte());
                }
                else if (robot instanceof RobotPatte){
                    ((RobotPatte) robot).setGraphe(donnees.getCarte());
                }
                else if (robot instanceof RobotRoue){
                    ((RobotRoue) robot).setGraphe(donnees.getCarte());
                }
                else{
                    throw new IllegalArgumentException("Ce n'est pas un robot qui est en entrée");
                }
                mapdaterobot.put(robot,(long) 0);
            }
            for (Incendie incendie : donnees.getIncendie()) {
            	HashMap<Robot, Long> closest = new HashMap<Robot, Long>();
            	for (Robot r: donnees.getRobot()){
            		if(r.getVitesse(incendie.getLacase().getNature()) != 0) {
            			closest.put(r, (long) r.getTempsArrivee(r.getPositionFictive(), incendie.getLacase())+mapdaterobot.get(r));
            		}
                }
            	
                long minDate = Integer.MAX_VALUE;
                Robot robot = null;
                for (Robot r : closest.keySet()){
                    if (closest.get(r) < minDate){
                        robot = r;
                        minDate = closest.get(r);
                    }
                }
                robot.setSimul(simul);
                boolean remplir = false;
                if ((mapdaterobot.get(robot) != 0) && !(robot instanceof RobotPatte)){
                    remplir = true;
                }
                long datesuivante2 = strategie.eteinte(mapdaterobot.get(robot), donnees, incendie, robot, min, remplir, robot.getPositionFictive());
                mapdaterobot.replace(robot, datesuivante2);
            }
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
