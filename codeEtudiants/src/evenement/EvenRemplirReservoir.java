package evenement;

import robots.*;
import simulation.*;
import exceptions.*;

/**  class  pour l'évènement: Remplir le réservoir
 * On considére que le réservoir est totalement ou partiellement vide 
 */
public class EvenRemplirReservoir extends Evenement{
    private Robot robot;
    private long datefinevenement;
    private Carte carte;

    /**Le constructeur: pour date finevenement on condidere ici que le reservoir est vide */
    public EvenRemplirReservoir(long date, Robot unrobot, Carte unecarte){
        super(date);
        robot =  unrobot;
        carte = unecarte;
        datefinevenement = date + robot.getTempsRemplissage()*60;
    }
    /**La méthode qui exécute concrètement l'évenement 
     * Verifie les conditions de remplissage pour chaque robot
     * notament la presence de l'eau
     * leve des exceptions dans le cas inverse
    */

    /**getter la datefinevenement */
    public long getDatefinevenement(){
        return datefinevenement;
    }
    public void execute(){
        boolean existenceeau = false;
        try{
            
            if ((robot instanceof RobotChenille) || (robot instanceof RobotRoue)){
                //System.out.println("position du robot est " + robot.getPosition().getLigne() +" , "+ robot.getPosition().getColonne());
                NatureTerrain voisinnature1;
                NatureTerrain voisinnature2;
                NatureTerrain voisinnature3;
                NatureTerrain voisinnature4;
                if (carte.voisinExiste(carte.getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()), Direction.EST) == true){
                    voisinnature1= (carte.getVoisin(robot.getPosition(), Direction.EST)).getNature();
                    if ((voisinnature1.equals(NatureTerrain.EAU))){
                        existenceeau = true;
                    }
                }if(carte.voisinExiste(carte.getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()), Direction.NORD) == true){
                    voisinnature2= (carte.getVoisin(robot.getPosition(), Direction.NORD)).getNature();
                    if ((voisinnature2.equals(NatureTerrain.EAU))){
                        existenceeau = true;
                    }
                }if (carte.voisinExiste(carte.getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()), Direction.OUEST) == true){
                    voisinnature3= (carte.getVoisin(robot.getPosition(), Direction.OUEST)).getNature();
                    if ((voisinnature3.equals(NatureTerrain.EAU))){
                        existenceeau = true;
                    }
                }if (carte.voisinExiste(carte.getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()), Direction.SUD) == true) {
                    voisinnature4= (carte.getVoisin(robot.getPosition(), Direction.SUD)).getNature();
                    if ((voisinnature4.equals(NatureTerrain.EAU))){
                        existenceeau = true;
                    }
                }
            }
             
            else if (robot instanceof Drone) {
            NatureTerrain natureposition = robot.getPosition().getNature();
            if ((natureposition.equals(NatureTerrain.EAU))){
                existenceeau = true;
            }
            }else if (robot instanceof RobotPatte) {
                throw new RemplissageRobotPatteException("Le robot à patte ne se remplit pas");
            }
            if (existenceeau == true){
                if (robot.getQuantiteEau() !=0){
                    datefinevenement = (robot.getQuantiteEau() *robot.getTempsRemplissage()*60)/(robot.getVolume());
                }else{
                    datefinevenement = robot.getTempsRemplissage();
                }
                robot.setQuantiteEau(robot.getVolume()); 
            }else{
                throw new RemplissageRobotException("Le robot ne verifie pas les conditions de remplissage: Déplacer le !"); 
            }
        }catch(RemplissageRobotPatteException e){
            // Ici, on va traiter l'exception du robotpatte
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);

        }catch(RemplissageRobotException e){
            // Ici, on va traiter l'exception du robot
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }        
    }
}
