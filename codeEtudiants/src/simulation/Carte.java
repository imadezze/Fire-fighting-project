package simulation;

import exceptions.*;
import java.util.zip.DataFormatException;


import gui.GUISimulator;
import gui.ImageElement;

/**  class mere de la carte */
public class Carte{

    /** La taille de chaque case
     * par default la longueur = largeur */
    private int tailleCases;

    /** Le nombre de lignes de la carte */
    private int nbLignes;

    /** Le nombre de colonnes de la carte */
    private int nbColonnes;

    /** La carte effective sous forme d'une matrice
     * de types Case */
    private Case carte[][];
    
    /** Cr�er la carte: Constructeur
     * @param nbLignes 
     * @param nbColonnes
     * @param tailleCases */

    public Carte(int nbLignes, int nbColonnes, int tailleCases){
        this.nbColonnes = nbColonnes;
        this.nbLignes = nbLignes;
        this.carte = new Case[nbLignes][nbColonnes];
        this.tailleCases = tailleCases;
    }
    
    /** getter pour le nombre de lignes */
    public int getNblignes(){
        return nbLignes;
    }

    /** getter pour le nombre de colonnes */
    public int getNbColonnes(){
        return nbColonnes;
    }

    /** getter pour la taille des cases */
    public int getTailleCases() {
        return tailleCases;
    }

    /** getter pour la Case */
    public Case getCase(int lig, int col){
        return carte[lig][col];
    }

    /** Chercher l'existence du voisin dans la carte
     * surtout v�rifier le d�bordement sur 
     * les 4 cot�s de la carte
     */
    public boolean voisinExiste(Case src, Direction dir){
        int ligne_recherche;
        int colone_recherche;
        switch(dir){
            case SUD : 
                ligne_recherche = src.getLigne() + 1;
                colone_recherche = src.getColonne();
                break;
            case NORD : 
                ligne_recherche = src.getLigne() - 1;
                colone_recherche = src.getColonne();
                break;
            case EST : 
                ligne_recherche = src.getLigne();
                colone_recherche = src.getColonne() + 1; 
                break; 
            case OUEST : 
                ligne_recherche = src.getLigne();
                colone_recherche = src.getColonne() - 1;
                break;   
            default : 
                ligne_recherche = -1;
                colone_recherche = -1;
                break;      
        }
        if (ligne_recherche < 0 || ligne_recherche > (nbLignes -1) || colone_recherche < 0 || colone_recherche >(nbColonnes-1)){
            return false;
        }
        return true;
    }

    /** Trouver le voisin d'une case suivant une direction */
    public Case getVoisin(Case src, Direction dir){
        int ligne_recherche;
        int colone_recherche;
        Case case_resultat = new Case(src.getLigne(),src.getColonne(),src.getNature());
        switch(dir){
            case SUD : 
                ligne_recherche = src.getLigne() + 1;
                colone_recherche = src.getColonne();
                break;
            case NORD : 
                ligne_recherche = src.getLigne() - 1;
                colone_recherche = src.getColonne();
                break;
            case EST : 
                ligne_recherche = src.getLigne();
                colone_recherche = src.getColonne() + 1;
                break;
            case OUEST : 
                ligne_recherche = src.getLigne();
                colone_recherche = src.getColonne() - 1;
                break;
            default : 
                ligne_recherche = -1;
                colone_recherche = -1;
                break;
        }
        try{
            if (ligne_recherche < 0 || ligne_recherche > (nbLignes -1) || colone_recherche < 0 || colone_recherche >(nbColonnes-1)){
                System.out.println("ligne recherchee est " + ligne_recherche + " colonne recherchee est " + colone_recherche);
            	throw new VoisinInexistantException("La case voisin n'existe pas");
            }
            case_resultat = carte[ligne_recherche][colone_recherche];
        }catch (VoisinInexistantException e) {
            // Ici, on va traiter l'exception en question
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return case_resultat;
    }

    /** setter une Case dans la carte */
    public void setCase(int lig, int col, Case une_case){
        carte[lig][col] = une_case;
    }
    
    /** dessiner la carte */
    public void drawCarte(DonneesSimulation donnees, GUISimulator gui) throws DataFormatException {
    	
    	int width = gui.getPanelWidth()/donnees.getCarte().getNbColonnes();
        int heigth = gui.getPanelHeight()/donnees.getCarte().getNblignes();
        int min;
        if (width <= heigth)
        	min = width;
        else
        	min = heigth;

    	for (int j=0; j<nbLignes; j++) {
    		for (int i=0; i<nbColonnes; i++) {
    			
    			ImageElement green = new ImageElement(i*min, j*min, "images/green.jpg", min, min, gui);
		        gui.addGraphicalElement(green);
    			
		        switch((donnees.getCarte()).getCase(j, i).getNature()) {
    			  
    			case EAU:
    				ImageElement image = new ImageElement(i*min, j*min, "images/eau.jpg", min, min, gui);
    		        gui.addGraphicalElement(image);
    		        break;
    		        
    			case FORET:
    				ImageElement image2 = new ImageElement(i*min, j*min, "images/tree.png", min, min, gui);
    		        gui.addGraphicalElement(image2);
    		        break;
    		        
    			case ROCHE:
    				ImageElement image3 = new ImageElement(i*min, j*min, "images/roche.png", min, min, gui);
    		        gui.addGraphicalElement(image3);
    		        break;
    				
    		   
    			case HABITAT:
    				ImageElement image5 = new ImageElement(i*min, j*min, "images/houses.png", min, min, gui);
    		        gui.addGraphicalElement(image5);
    		        break;
			
    			default:
					break;
    			}
    			
    		}
    	}
    }
       
}