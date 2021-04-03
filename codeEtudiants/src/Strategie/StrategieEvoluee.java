package Strategie;

import evenement.*;
import robots.*;
import simulation.*;
import exceptions.*;
public class StrategieEvoluee{

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
    public long eteinte(long date, DonneesSimulation donnees,Incendie incendie, Robot robot, int minn, boolean remplir, Case caseInitiale){
    	robot.setEtat(true);
        long datesuivante = date;
        Simulateur simul = robot.getSimul();
        //Case caserobotinitial = robot.getPosition();
        Case caserobotinitial = caseInitiale;
        try{

            Case caseplusproche = null;
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
            if (remplir){
                
                Case caseEau = null;
                if (robot instanceof Drone){ 
                    caseEau = ((Drone) robot).cherchereau(caserobotinitial,donnees.getCarte());
                }
                else if (robot instanceof RobotChenille){
                    caseEau = ((RobotChenille) robot).cherchereau(caserobotinitial,donnees.getCarte());
                }
                else if (robot instanceof RobotRoue){
                    caseEau = ((RobotRoue) robot).cherchereau(caserobotinitial,donnees.getCarte());
                }
                long dateEau = robot.deplacerRobot(datesuivante, donnees.getCarte(), minn, caserobotinitial,caseEau);
                caserobotinitial = caseEau;
                //robot.setPosition(caseEau);
                datesuivante = dateEau;
                Simulateur simulEau = robot.getSimul();
                EvenRemplirReservoir eeau = new EvenRemplirReservoir(datesuivante, robot, donnees.getCarte());
                simulEau.ajouteEvenement(eeau);
                datesuivante = eeau.getDatefinevenement();
            }

            long date2 = robot.deplacerRobot(datesuivante, donnees.getCarte(), minn, caserobotinitial, incendie.getLacase());
            caserobotinitial = incendie.getLacase();
            //robot.setPosition(incendie.getLacase());
            datesuivante = date2;
            if (robot instanceof Drone){ 
                caseplusproche = ((Drone) robot).cherchereau(caserobotinitial,donnees.getCarte());
            }
            else if (robot instanceof RobotChenille){
                caseplusproche = ((RobotChenille) robot).cherchereau(caserobotinitial,donnees.getCarte());
            }
            else if (robot instanceof RobotRoue){
                caseplusproche = ((RobotRoue) robot).cherchereau(caserobotinitial,donnees.getCarte());
            }
            int intensiteincendie = incendie.getNbrLitres();
            EvenVerserleau e1 = new EvenVerserleau(datesuivante, incendie, robot, donnees, intensiteincendie);
            simul.ajouteEvenement(e1);
            datesuivante = e1.getDatefinevenement();
            EvenVerserleauFin e5 = new EvenVerserleauFin(datesuivante, incendie, robot, donnees);
            simul.ajouteEvenement(e5);
            intensiteincendie = diminuerintensite(robot.getQuantiteEau(), intensiteincendie);
            while (intensiteincendie != 0){
                if (caseplusproche == null){
                    if (robot instanceof RobotPatte){
                        throw new IllegalArgumentException("Le robot à patte ne se remplit pas, il doit normalement faire le job");
                    }
                    throw new RemplissageRobotException("il y'a une erreur dans le code pour la recherche de l'eau ou il n'ya pas d'eau possible pour ce robot dans la carte ");
                }
                //long tempsdeplacement =(long) robot.getTempsArrivee(caseplusproche);
                robot.setSimul(simul);
                long dateretourne = robot.deplacerRobot(datesuivante, donnees.getCarte(),minn , caserobotinitial ,caseplusproche);
                caserobotinitial = caseplusproche;
                //robot.setPosition(caseplusproche);
                Simulateur simul3 = robot.getSimul();
                EvenRemplirReservoir e2 = new EvenRemplirReservoir(dateretourne, robot, donnees.getCarte());
                simul3.ajouteEvenement(e2);
                datesuivante = e2.getDatefinevenement();
                long dateretourne2 = robot.deplacerRobot(datesuivante, donnees.getCarte(),minn , caserobotinitial ,incendie.getLacase());
                caserobotinitial = incendie.getLacase();
                //robot.setPosition(incendie.getLacase());
                Simulateur simul2 = robot.getSimul();
                //datesuivante = datesuivante +dateretourne2+dateretourne;
                datesuivante = dateretourne2;
                EvenVerserleau e3 = new EvenVerserleau(datesuivante, incendie, robot, donnees, intensiteincendie);
                simul2.ajouteEvenement(e3);
                datesuivante = e3.getDatefinevenement();
                EvenVerserleauFin e4 = new EvenVerserleauFin(datesuivante, incendie, robot, donnees);
                simul2.ajouteEvenement(e4);
                intensiteincendie = diminuerintensite(robot.getVolume(), intensiteincendie);  
            }
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
			e.printStackTrace();
			System.exit(1);
        }
        catch (RemplissageRobotException e){
            System.out.println(e);
			e.printStackTrace(); 
        }
        
        //robot.setPosition(caserobotinitial);
        robot.setPositionFictive(incendie.getLacase());
        return datesuivante;
    }
}