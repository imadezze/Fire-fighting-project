package robots;

import simulation.*;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

import dijkstra.Graphe;
import dijkstra.Sommet;
import evenement.Evenement;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.ImageElement;

/** classe mere des robots */
public abstract class Robot {
	/** position du robot */
	private Case position;
	
	/** position du robot */
	private Case positionFictive;

	/** vitesse du robot */
	private int vitesse;
	
	/** volulme du réservoir */
	private int volume;
	
	/** etat du robot
	 * true => occupé
	 * false => non occupé
	*/
	private boolean etat;

	/** getter pour position */
	public Case getPositionFictive() {
		return positionFictive;
	}
	/**setter la position fictive */
	public void setPositionFictive(Case positionfictive) {
		positionFictive = positionfictive;
	}

	/** accesseur à l'etat */
	public boolean getEtat() {
		return etat;
	}
	/** setter pour l'etat'*/
	public void setEtat(Boolean un_etat){
		this.etat = un_etat;
	}
	/** image du robot dans le guiSimulator*/
	private ImageElement image;
	
	/**Simulateur pour les evenements */
	private Simulateur simul;

	/** getter pour simulateur */
	public Simulateur getSimul() {
		return simul;
	}

	/**Setter pour simulateur */
	public void setSimul(Simulateur simul1) {
		simul = simul1;
	}

	/** getter pour image */
	public ImageElement getImage() {
		return image;
	}
	

	/** accesseur à la vitesse */
	public int getVitesse() {
		return vitesse;
	}
	
	/** quantité dont dispose le robot, inferieure au volume */
	private int quantiteEau;
	
	/** getter pour le qantité d'eau*/
	public int getQuantiteEau() {
		return quantiteEau;
	}
	
	/** temps du remplissage du reservoir*/
	private int tempsRemplissage;
	
	/**Intervention unitaire */
	private double intervention;

	/** getter pour temps de remplissage */
	public double getIntervention() {
		return intervention;
	}

	/** getter pour temps de remplissage */
	public int getTempsRemplissage() {
		return tempsRemplissage;
	}

	/** setter pour le qantité d'eau*/
	public void setQuantiteEau(int quantiteEau) {
		this.quantiteEau = quantiteEau;
	}

	/** getter pour position */
	public Case getPosition() {
		return position;
	}
	
	/** construit un robot */
	public Robot(Case position, int volume, int tempsRemplissage, int vitesse, double intervention) {
		this.position = position;
		this.positionFictive = position;
		this.volume = volume;
		this.quantiteEau = volume;
		this.tempsRemplissage = tempsRemplissage;
		this.vitesse = vitesse;
		this.intervention = intervention;
		this.etat = false;
	}

	/** getter pour volume */
	public int getVolume() {
		return volume;
	}
	
	/** setter pour position */
	public void setPosition(Case position) {
		this.position = position;
	}

	/** renvoie la vitesse du robot sur le terrain */
	public abstract double getVitesse(NatureTerrain terrain);
	
	/** deverse de l'eau, et décrémente le reservoir */
	public void deverserEau(int vol) {
		if (quantiteEau == 0) {
			throw new IllegalArgumentException("le reservoir du robot est vide, veuillez le remplir");
		}
		else if (quantiteEau < vol) {
			quantiteEau = 0;
		}
		else {
			quantiteEau = quantiteEau - vol;
		}
	}
	
	/** remplie le reservoir */
	public void remplirReservoir() {
		quantiteEau = volume;
	}
	
	/** dessiner les robots */
	public void draw(Carte carte, GUISimulator gui) throws DataFormatException{
		
		int width = gui.getPanelWidth()/carte.getNbColonnes();
        int heigth = gui.getPanelHeight()/carte.getNblignes();
        int min = heigth;
        if (width <= heigth)
        	min = width;
        int ligne = position.getLigne();
		int colonne = position.getColonne();
		if (this instanceof RobotChenille) {
			image = new ImageElement(colonne*min, ligne*min, "images/RobotChenille.png", min, min, gui);
	        gui.addGraphicalElement(image);
		}
		else if (this instanceof RobotPatte) {
			image = new ImageElement(colonne*min, ligne*min, "images/RobotPatte.png", min, min, gui);
	        gui.addGraphicalElement(image);
		}
		else if (this instanceof RobotRoue) {
			image = new ImageElement(colonne*min, ligne*min, "images/RobotRoue.png", min, min, gui);
	        gui.addGraphicalElement(image);
		}
		else if (this instanceof Drone) {
			image = new ImageElement(colonne*min, ligne*min, "images/Drone.png", min, min, gui);
	        gui.addGraphicalElement(image);
		}
	}
	
	/**
	 * Retourne le temps nécessaire pour arriver à une case donnee 
	 * @param caseDestination
	 */
	public abstract double getTempsArrivee(Case caseSource,Case caseDestination);
	
	/**
	 * fonction qui crée les événements de déplacement du robot, et renvoie le date d'arrivee
	 * @param date
	 * @param carte
	 * @param minn
	 * @param caseDestination
	 * @return
	 */
	public abstract long deplacerRobot(long date, Carte carte, int minn,Case caseSource,Case caseDestination);
}
