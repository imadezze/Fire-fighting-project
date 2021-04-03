package evenement;

import robots.Robot;
import simulation.*;
import exceptions.*;

/**  class  pour l'évènement: déplacement d'un robot */
public class EvenDeplacementRobot extends Evenement{

    /**La ligne d'arrivee */
    private int ligne_arrive;

    /**La colonne d'arrivee */
    private int colone_arrive;

    /** minmum entre hauteur et largeur*/
    int min;
    
    /** La carte en question*/
    private Carte unecarte;

    /**Le robot en question */
    private Robot robot;

    public Robot getRobot() {
		return robot;
	}

	/**Le Constructeur */
    public EvenDeplacementRobot(long date, int ligne, int colone, Robot unrobot, Carte lacarte, int minn){
        super(date);
        ligne_arrive = ligne;
        colone_arrive = colone;
        robot = unrobot;
        unecarte = lacarte;
        min = minn;
    }

    /**La méthode qui exécute concrètement l'évenement */
    public void execute(){
        try{
            if (ligne_arrive >= 0 && ligne_arrive <= (unecarte.getNblignes() -1) && colone_arrive >= 0 && colone_arrive <= (unecarte.getNbColonnes()-1)){
                Case casearrivee = unecarte.getCase(ligne_arrive, colone_arrive);
                
                int ligne_avant = robot.getPosition().getLigne();
                int colonne_avant = robot.getPosition().getColonne();
                
                
                robot.setPosition(casearrivee);
                
                
                robot.getImage().translate((robot.getPosition().getColonne()-colonne_avant)*min, (robot.getPosition().getLigne()-ligne_avant)*min);
            }else{
                throw new CaseInexistanteException("La case sort de la carte");
            }
        }catch(CaseInexistanteException e){
            // Ici, on va traiter l'exception en question
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}