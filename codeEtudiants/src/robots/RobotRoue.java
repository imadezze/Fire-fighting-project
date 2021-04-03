package robots;

import simulation.*;

import java.util.ArrayList;
import java.util.LinkedList;

import dijkstra.Arete;
import dijkstra.Graphe;
import dijkstra.Sommet;
import evenement.EvenDeplacementRobot;
import evenement.Simulateur;
import exceptions.*;

public class RobotRoue extends Robot{
	/** graphe selon une instance de robot à roues*/
	private static Graphe graphe;
	
	public static Graphe getGraphe() {
		return graphe;
	}
	
	public void setGraphe(Carte carte) {
		ArrayList<Arete> aretes = new ArrayList<Arete>();
		ArrayList<Case> cases = new ArrayList<Case>();
		
		for (int i = 0; i <carte.getNblignes(); i++) {
			for (int j = 0; j <carte.getNbColonnes(); j++) {
				if(getVitesse(carte.getCase(i, j).getNature()) != 0) {
					cases.add(carte.getCase(i, j));
				}
			}
		}
		
		// creation des aretes
		for (int index = 0; index < cases.size(); index++) {
			// temps de parcours des cases
			double temps = carte.getTailleCases()*3.6/ getVitesse(cases.get(index).getNature());
			
			if(cases.get(index).getLigne() < carte.getNblignes()-1) {
				if(getVitesse(carte.getCase(cases.get(index).getLigne()+1,cases.get(index).getColonne()).getNature()) != 0) {
					int toIndex = cases.indexOf(carte.getCase(cases.get(index).getLigne()+1,cases.get(index).getColonne()));
					aretes.add(new Arete(index, toIndex, temps));
				}
			}
			if(cases.get(index).getColonne() < carte.getNbColonnes()-1) {
				if(getVitesse(carte.getCase(cases.get(index).getLigne(),cases.get(index).getColonne()+1).getNature()) != 0) {
				int toIndex = cases.indexOf(carte.getCase(cases.get(index).getLigne(),cases.get(index).getColonne()+1));
					aretes.add(new Arete(index, toIndex, temps));
				}
			}
		}
		
		Arete[] listAretes = aretes.toArray(new Arete[aretes.size()]);
		graphe = new Graphe(listAretes, cases);
	}
	
	/** constructeur, vitesse par default*/
	public RobotRoue(Case position) {
		super(position, 5000, 10, 80, 0.05);
	}

	/** constructeur, vitesse donnée */
	public RobotRoue(Case position, int vitesse) {
		super(position, 5000, 10, vitesse, 0.05);
		try {
			if (vitesse > 150 || vitesse <= 0) throw new InvalidVitesseException("La vitesse entrée n'est pas valide");
		}
		catch (InvalidVitesseException e) {
			// Ici, on va traiter l'exception en question
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
		}
	}
	
	/** renvoie la vitesse du robot sur le terrain, en km/h */
	public double getVitesse(NatureTerrain terrain) {
		int vitesse = this.getVitesse();
		switch(terrain) {
			case TERRAIN_LIBRE:
				return vitesse;
			case HABITAT:
				return vitesse;
			default:
				return 0;
		}
	}
	
	/**
	 * Retourne le temps nécessaire pour arriver à une case donnee
	 */
	public double getTempsArrivee(Case caseSource,Case caseDestination){
		try {
			if(graphe == null) {
				throw new IllegalArgumentException("Vous devez d'abord initialiser le graphe");
			}
			/*if(getVitesse(caseDestination.getNature()) == 0) {
				throw new IllegalArgumentException("Case inaccessible pour le robot");
			}*/
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
		//ArrayList<Sommet> chemin = graphe.shortestDistancesAndPath(graphe.getListCases().indexOf(this.getPosition())).get(caseDestination.getSommet());
		ArrayList<Sommet> chemin = graphe.shortestDistancesAndPath(graphe.getListCases().indexOf(caseSource)).get(caseDestination.getSommet());
		return caseDestination.getSommet().getDistanceFromSource();
	}

	/** fonction qui crée les événements de déplacement du robot, et renvoie le date d'arrivee*/
	public long deplacerRobot(long date, Carte carte, int minn, Case caseSource,Case caseDestination){
		long dateEvent = date;
		Simulateur simul = this.getSimul();
		try{
			if(getVitesse(caseDestination.getNature()) == 0) {
				throw new IllegalArgumentException("Case inaccessible pour le robot");
			}
			//ArrayList<Sommet> chemin = getGraphe().shortestDistancesAndPath(graphe.getListCases().indexOf(this.getPosition())).get(caseDestination.getSommet());
			ArrayList<Sommet> chemin = getGraphe().shortestDistancesAndPath(graphe.getListCases().indexOf(caseSource)).get(caseDestination.getSommet());
			for (Sommet s : chemin) {
				int ligne = s.getLacase().getLigne();
				int colonne = s.getLacase().getColonne();
				EvenDeplacementRobot e = new EvenDeplacementRobot(dateEvent, ligne, colonne, this, carte, minn);
				dateEvent = date + (long) s.getDistanceFromSource();
				simul.ajouteEvenement(e);
			}
		}catch(IllegalArgumentException e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
		this.setSimul(simul);
		return dateEvent;
	}

	/**
	 * cherche et retourne la case la plus proche pour remplir l'eau en se basant sur les plus courts chemins 
	 * @param carte
	 * @return
	 */
	public Case cherchereau(Case caseSource, Carte carte){
        // Cherche la case la plus proche
		Case caseminimum = null;
		int indiceinitialisation = 0;
        long t1 = 0;
        for (int lig = 0; lig < carte.getNblignes(); lig++) {
            for (int col = 0; col < carte.getNbColonnes(); col++) {
				if (carte.getCase(lig, col).getNature().equals(NatureTerrain.EAU)){
					LinkedList<Case> listevoisin = new LinkedList<Case>();
					if (carte.voisinExiste(carte.getCase(lig, col), Direction.EST) == true){
						listevoisin.add(carte.getVoisin(carte.getCase(lig, col), Direction.EST));
					}if(carte.voisinExiste(carte.getCase(lig, col), Direction.NORD) == true){
						listevoisin.add(carte.getVoisin(carte.getCase(lig, col), Direction.NORD));
					}if (carte.voisinExiste(carte.getCase(lig, col), Direction.OUEST) == true){
						listevoisin.add(carte.getVoisin(carte.getCase(lig, col), Direction.OUEST));
					}if (carte.voisinExiste(carte.getCase(lig, col), Direction.SUD) == true) {
						listevoisin.add(carte.getVoisin(carte.getCase(lig, col), Direction.SUD));
					}
					for (Case une_case : listevoisin){
						long tem = 0;
						if(getVitesse(une_case.getNature()) != 0){
							tem = (long) this.getTempsArrivee(caseSource ,une_case);
							if (tem < t1 || indiceinitialisation == 0){
								if (indiceinitialisation == 0){
									caseminimum = une_case;
									indiceinitialisation = 1;
								}else{
									caseminimum = une_case;
								}
								t1 = tem;
							}
						}
					}
                } 
            }
		}
		return caseminimum;	
	}	
}
