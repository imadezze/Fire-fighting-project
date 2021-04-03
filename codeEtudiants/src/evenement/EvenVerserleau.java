package evenement;

import simulation.*;
import robots.Drone;
import robots.Robot;
import robots.RobotPatte;

/**  class  pour l'évènement: Verser l'eau sur une incendie */
public class EvenVerserleau extends Evenement{

    /** L'incendie invoque en question*/
    private Incendie incendie;

    /**La date de fin de l'evenement */
    private long datefinevenement;

    /** Le robot pret à essayer d'eteindre l'incedie */
    private Robot robot;
    
    /** donnee à simuler */
    DonneesSimulation donnee;

    /**getter la datefinevenement */
    public long getDatefinevenement(){
        return datefinevenement;
    }

    /**Le Constructeur */
    public EvenVerserleau(long date, Incendie uneincendie, Robot unrobot, DonneesSimulation donee, int intensite){
        super(date);
        incendie = uneincendie;
        robot = unrobot;
        donnee = donee;
        if (robot instanceof Drone){
            datefinevenement = date + (long) robot.getIntervention();
        }
        /**else if(robot instanceof RobotPatte){
            datefinevenement = date + intensite*robot.getIntervention();
            System.out.println(datefinevenement);
        }*/
        else if (intensite > robot.getQuantiteEau()){
            datefinevenement = date + (long)(robot.getQuantiteEau()*robot.getIntervention());
        }
        else{
            datefinevenement = date + (long)(intensite*robot.getIntervention());
        }
    }

    /**La methode qui exécute concrètement l'evenement */
    public void execute(){
        robot.setEtat(true);
        if (incendie.getNbrLitres() > robot.getQuantiteEau()){
        	int intensite = incendie.getNbrLitres();
            incendie.setNbrLitres((incendie.getNbrLitres() - robot.getQuantiteEau()));
            robot.deverserEau(intensite);
        }else{
            robot.deverserEau(incendie.getNbrLitres());
            incendie.setNbrLitres(0);
        }
    }
}
