package robots;

import java.util.ArrayList;

import dijkstra.Arete;
import dijkstra.Graphe;
import dijkstra.Sommet;
import evenement.EvenDeplacementRobot;
import evenement.Simulateur;
import simulation.*;

public class RobotPatte extends Robot{
	/** graphe selon une instance de robot à pattes*/
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
	
	/** constructeur */
	public RobotPatte(Case position) {
		super(position, Integer.MAX_VALUE, 0, 30, 0.1);
		this.setQuantiteEau(this.getVolume());
	}

	/** constructeur, si vitesse donnée, imposée = 30 */
	public RobotPatte(Case position, int vitesse) {
		super(position, Integer.MAX_VALUE, 0, 30, 0.1);
		this.setQuantiteEau(this.getVolume());
	}
	
	/** renvoie la vitesse du robot sur le terrain, en km/h */
	public double getVitesse(NatureTerrain terrain) {
		switch(terrain) {
			case EAU:
				return 0;
			case ROCHE:
				return 10;
			default:
				return 30;
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
		ArrayList<Sommet> chemin = getGraphe().shortestDistancesAndPath(graphe.getListCases().indexOf(caseSource)).get(caseDestination.getSommet());
		long dateEvent = date;
		for (Sommet s : chemin) {
			int ligne = s.getLacase().getLigne();
			int colonne = s.getLacase().getColonne();
			EvenDeplacementRobot e = new EvenDeplacementRobot(dateEvent, ligne, colonne, this, carte, minn);
			dateEvent = date + (long) s.getDistanceFromSource();
			simul.ajouteEvenement(e);
		}
		/*for(Sommet s : chemin) {
			System.out.println("la case à la ligne " + s.getLacase().getLigne() + " et colonne " + s.getLacase().getColonne());
		}*/	
		this.setSimul(simul);
		return dateEvent;
	}

}
