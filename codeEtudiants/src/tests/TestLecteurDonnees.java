package tests;
import simulation.*;
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        
        try {
            //LecteurDonnees.lire(args[0]);
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            System.out.println("hello");
            System.out.println((donnees.getCarte()).getCase(1, 1).getNature());
            System.out.println((donnees.getIncendie()).getFirst().getNbrLitres());
            System.out.println((donnees.getRobot()).getLast().getVolume());
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

