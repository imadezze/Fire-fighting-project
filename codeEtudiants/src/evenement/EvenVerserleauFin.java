package evenement;

import robots.Robot;
import robots.RobotRoue;
import simulation.*;

/**  class  pour l'évènement: La fin du versement de l'eau sur une incendie */
public class EvenVerserleauFin extends Evenement{
     /** donnee à simuler */
     DonneesSimulation donnee;

    /** L'incendie invoque en question*/
    private Incendie incendie;
    
    /** robot à libérer*/
    Robot robot;
    
     /**Le Constructeur */
    public EvenVerserleauFin(long date, Incendie uneincendie, Robot unrobot ,DonneesSimulation donee){
        super(date);
        incendie = uneincendie;
        donnee = donee;
        robot = unrobot;
        robot.setEtat(false);
    }

    /**La methode qui exécute concrètement l'evenement */
    public void execute(){
        if (incendie.getNbrLitres() == 0){
            incendie.eteindre(donnee);
        }
    }
}
