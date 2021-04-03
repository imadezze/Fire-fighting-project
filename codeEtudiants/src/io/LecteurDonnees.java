package io;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import simulation.*;
import robots.*;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Remplis les données nécessaires
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees, donnees)
     * @param fichierDonnees nom du fichier à lire
     * @param donnees les données lues depuis le fichiers 
     */
    public static void lire(String fichierDonnees, DonneesSimulation donnees)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte(donnees);
        lecteur.lireIncendies(donnees);
        lecteur.lireRobots(donnees);
        scanner.close();
        System.out.println("\n == Lecture terminee");
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * crée des cases de la carte
     * @param donnees
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);
            donnees.setCarte(carte);
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col, donnees);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }




    /**
     * Lit et affiche les donnees d'une case.
     * crée une case de la carte
     * @param donnees
     */
    private void lireCase(int lig, int col, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);
            creeCase(lig, col, nature, donnees);
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }


    /**
     * Lit et affiche les donnees des incendies.
     * crée des incendies de la carte
     * @param donnees
     */
    private void lireIncendies(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            LinkedList<Incendie> Incendies = new LinkedList<Incendie>();
            donnees.setIncendies(Incendies);
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i, donnees);

            }
            

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * crée une incendie de la carte
     * @param i
     * @param donnes
     */
    private void lireIncendie(int i, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            creeIncendie(lig, col, intensite, donnees);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     * crée des robots de la carte
     * @param donnees
     */
    private void lireRobots(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            LinkedList<Robot> Robots = new LinkedList<Robot>();
            donnees.setRobots(Robots);
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i, donnees);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * crée un robot de la carte
     * @param i
     * @param donnees
     */
    private void lireRobot(int i, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");
            int vitesse;
            if (s == null) {
                System.out.print("valeur par defaut");
                // la vitesse nul ne sera pas utilisé
                vitesse = 0;
            } else {
                vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();
            creeRobot(s, vitesse, type, lig, col, donnees);
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }

    // La partie ajoutée
    /** Donnée de simulation sera fixé après l'initiation */
    static DonneesSimulation resultat = new DonneesSimulation();

    /** créer effectivement les données (main pour les données)*/
    public static DonneesSimulation creeDonnees(String fichierDonnees) throws FileNotFoundException, DataFormatException{
        lire(fichierDonnees, resultat);
        return resultat;
    }

    /**créer une case dans les données*/
    public void creeCase(int lig, int col, NatureTerrain nature, DonneesSimulation donnees){
        Case resultat = new Case(lig, col, nature);
        donnees.setCase_donnees(lig, col, resultat);
    }

    /** créer un robot dans les données */
    public void creeRobot(String s, int vitesse, String type ,int lig, int col, DonneesSimulation donnees){
        Case la_case_robot = (resultat.getCarte()).getCase(lig, col);
        // Eh oui, on utilise le polymorphisme dans les robots

        try{
            // s indique l'existence d'une vitesse donnée
            if (s == null){
                // Ici, on appelle le constructeur sans vitesse
                if (type.equals("CHENILLES")){
                    RobotChenille robot = new RobotChenille(la_case_robot);
                    donnees.setRobot(robot);
                }else if (type.equals("PATTES")){
                    RobotPatte robot = new RobotPatte(la_case_robot);
                    donnees.setRobot(robot);
                }else if (type.equals("ROUES")){
                    RobotRoue robot = new RobotRoue(la_case_robot);
                    donnees.setRobot(robot);
                }else if (type.equals("DRONE")){
                    Drone robot = new Drone(la_case_robot);
                    donnees.setRobot(robot);
                }else{
                    throw new IllegalArgumentException("Le robot n'existe pas");
                }
            }else{

                // Ici, on appelle le constructeur avec vitesse
                if (type.equals("CHENILLES")){
                    RobotChenille robot = new RobotChenille(la_case_robot, vitesse);
                    donnees.setRobot(robot);
                }else if (type.equals("PATTES")){
                    RobotPatte robot = new RobotPatte(la_case_robot, vitesse);
                    donnees.setRobot(robot);
                }else if (type.equals("ROUES")){
                    RobotRoue robot = new RobotRoue(la_case_robot, vitesse);
                    donnees.setRobot(robot);
                }else if (type.equals("DRONE")){
                    Drone robot = new Drone(la_case_robot, vitesse);
                    donnees.setRobot(robot);
                }else{
                    throw new IllegalArgumentException("Le robot n'existe pas");
                }   
            }
        }catch (IllegalArgumentException e) {
			// Ici, on va traiter l'exception en question
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
		}
        

    }

    /** créer une incendie dans les données en prenant 
    * l'intensité = nombre de litres  
    */
    public void creeIncendie(int lig,int col, int intensite, DonneesSimulation donnees){ 
        Case la_case_incendie = (resultat.getCarte()).getCase(lig, col);
        Incendie incendie = new Incendie(la_case_incendie, intensite);
        donnees.setIncendie(incendie);
    }
}
