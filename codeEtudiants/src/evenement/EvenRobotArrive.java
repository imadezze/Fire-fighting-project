package evenement;

import robots.Robot;
import simulation.*;
import exceptions.RobotNonArriveException;

/**  class  pour l'évènement: Prévention de l'arrivée d'un robot */
public class EvenRobotArrive extends Evenement{
    /** La case étudiée  */
    private Case casearrivee;

    /** Le robot en question */
    private Robot robot;

    /**Le Constructeur */
    public EvenRobotArrive(long date, Case unecase, Robot unrobot){
        super(date);
        casearrivee = unecase;
        robot = unrobot;
    }

    /** Le méthode qui execute concrètement l'évenement 
     * Affiche un message si le robot est arrivé
     * lève une exception dans le cas inverse  
    */
    public void execute(){
        try{
            if (robot.getPosition() != casearrivee){
                System.out.println("Le robot est arrivée");
            }else{
                throw new RobotNonArriveException("Le robot n'est pas arrivée, il est perdu en route !");
            }

        }catch (RobotNonArriveException e){
            // Ici, on va traiter l'exception en question
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}