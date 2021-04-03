package robots;

import simulation.*;
import exceptions.*;

import java.util.ArrayList;

import dijkstra.*;
import evenement.EvenDeplacementRobot;
import evenement.Simulateur;

/** drone, un type de robots qui vole */
public class Drone extends Robot{
	/** graphe selon une instance de drone*/
	private static Graphe graphe;
	
	
	public static Graphe getGraphe() {
		return graphe;
	}

	public void setGraphe(Carte carte) {
		ArrayList<Arete> aretes = new ArrayList<Arete>();
		ArrayList<Case> cases = new ArrayList<Case>();
		
		for (int i = 0; i <carte.getNblignes(); i++) {
			for (int j = 0; j <carte.getNbColonnes(); j++) {
				cases.add(carte.getCase(i, j));
			}
		}
		
		// creation des aretes
		for (int index = 0; index < cases.size(); index++) {
			// temps de parcours des cases
			double temps = carte.getTailleCases()*3.6/ getVitesse(cases.get(index).getNature());
			
			if(cases.get(index).getLigne() < carte.getNblignes()-1) {
				int toIndex = cases.indexOf(carte.getCase(cases.get(index).getLigne()+1,cases.get(index).getColonne()));
				aretes.add(new Arete(index, toIndex, temps));
			}
			if(cases.get(index).getColonne() < carte.getNbColonnes()-1) {
				int toIndex = cases.indexOf(carte.getCase(cases.get(index).getLigne(),cases.get(index).getColonne()+1));
				aretes.add(new Arete(index, toIndex, temps));
			}
		}
		
		Arete[] listAretes = aretes.toArray(new Arete[aretes.size()]);
		graphe = new Graphe(listAretes, cases);
	}

	/** constructeur de classe Drone, vitesse par défault */
	public Drone(Case position) {
		super(position, 10000, 30, 100, 30);
	}
	
	/** constructeur de classe Drone, vitesse donnée*/
	public Drone(Case position, int vitesse) {
		super(position, 10000, 30, vitesse, 30);
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
		return this.getVitesse();
	}
	
	/**
	 * Retourne le temps nécessaire pour arriver à une case donnee
	 */
	public double getTempsArrivee(Case caseSource,Case caseDestination){
		try {
			if(getGraphe() == null) {
				throw new IllegalArgumentException("Vous devez d'abord initialiser le graphe");
			}
			if(getVitesse(caseDestination.getNature()) == 0) {
				throw new IllegalArgumentException("Case inaccessible pour le robot");
			}
			
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
		Simulateur simul = this.getSimul();
		//ArrayList<Sommet> chemin = getGraphe().shortestDistancesAndPath(graphe.getListCases().indexOf(this.getPosition())).get(caseDestination.getSommet());
		ArrayList<Sommet> chemin = getGraphe().shortestDistancesAndPath(graphe.getListCases().indexOf(caseSource)).get(caseDestination.getSommet());
		long dateEvent = date;
		for (Sommet s : chemin) {
			int ligne = s.getLacase().getLigne();
			int colonne = s.getLacase().getColonne();
			EvenDeplacementRobot e = new EvenDeplacementRobot(dateEvent, ligne, colonne, this, carte, minn);
			dateEvent = date + (long) s.getDistanceFromSource();
			simul.ajouteEvenement(e);
		}
		this.setSimul(simul);
		return dateEvent;
	}
	/**
	 * cherche et retourne la case la plus proche pour remplir l'eau en se basant sur les plus courts chemins 
	 * @param carte
	 * @return case
	 */
	public Case cherchereau(Case caseSource, Carte carte){
        // Cherche la case la plus proche
		Case caseminimum = null;
		int indiceinitialisation = 0;
        long t1 = 0;
        for (int lig = 0; lig < carte.getNblignes(); lig++){
            for (int col = 0; col < carte.getNbColonnes(); col++){
                long t = 0;
				if (carte.getCase(lig, col).getNature().equals(NatureTerrain.EAU)){
					t = (long) this.getTempsArrivee(caseSource ,carte.getCase(lig, col));
					if (t < t1 || indiceinitialisation == 0){ 
						if (indiceinitialisation == 0){
							caseminimum = carte.getCase(lig, col);
							indiceinitialisation = 1;
						}else{
							caseminimum = carte.getCase(lig, col);	
						}
						t1 = t;
					}
				}
 
            }
		}
		return caseminimum;	
	}

	@Override
	/** deverse de l'eau, et décrémente le reservoir */
	public void deverserEau(int vol) {
		if (this.getQuantiteEau() == 0) {
			throw new IllegalArgumentException("le reservoir du robot est vide, veuillez le remplir");
		}
		else {
			this.setQuantiteEau(0);
		}	
	}
}
