package Strategie;

import evenement.*;
import robots.*;
import simulation.*;
public class StrategieElement{

    /**
     * Fonction intermediaire pour savoir le nombre et les paramétres des évenements qu'on va créer pour eteindre l'incendie
     * @param quantiteaurobot
     * @param intensitedebut
     * @return
     */
    public int diminuerintensite(int quantiteaurobot,int intensitedebut){
        int intensite = intensitedebut;
        if (intensitedebut > quantiteaurobot){
            intensite = intensitedebut - quantiteaurobot;
        }
        else{
            intensite = 0;
        }
        return intensite;
    }

    /**
     * Fonction qui permet de coder les evenements pour eteindre une incendie donnee
     * @param date
     * @param donnees
     * @param incendie
     * @param robot
     * @param minn
     * @return
     */
    public long eteinte(long date, DonneesSimulation donnees,Incendie incendie, Robot robot, int minn, Case caseInitiale){
    	robot.setEtat(true);
        long datesuivante = date;
        Simulateur simul = robot.getSimul();
        Case caserobotinitial = caseInitiale;
        try{
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

            long date2 = robot.deplacerRobot(datesuivante, donnees.getCarte(), minn, caserobotinitial, incendie.getLacase());
            caserobotinitial = incendie.getLacase();
            datesuivante = date2;
            int intensiteincendie = incendie.getNbrLitres();
            EvenVerserleau e1 = new EvenVerserleau(datesuivante, incendie, robot, donnees, intensiteincendie);
            simul.ajouteEvenement(e1);
            datesuivante = e1.getDatefinevenement();
            EvenVerserleauFin e5 = new EvenVerserleauFin(datesuivante, incendie, robot, donnees);
            simul.ajouteEvenement(e5);
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
			e.printStackTrace();
			System.exit(1);
        }
        
        robot.setPositionFictive(incendie.getLacase());
        robot.setEtat(true);
        return datesuivante;
    }
}