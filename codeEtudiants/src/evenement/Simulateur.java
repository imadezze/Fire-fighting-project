package evenement;

import java.util.ArrayList;
import java.util.LinkedList;
import robots.*;
import gui.Simulable;
import gui.GUISimulator;
import simulation.DonneesSimulation;
import simulation.*;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import gui.ImageElement;

public class Simulateur implements Simulable{
	
	private long dateSimulation = 0;
	private DonneesSimulation donnees;
	private LinkedList<Robot> robots = new LinkedList<Robot>();
	private LinkedList<Incendie> incendies = new LinkedList<Incendie>();
	private GUISimulator gui;
	
	private SortedMap<Long, ArrayList<Evenement>> evenements;

	public Simulateur(long dateSimulation, DonneesSimulation donnees) {
		super();
		this.donnees = donnees;
		this.dateSimulation = dateSimulation;
		this.robots = new LinkedList<Robot>();
		for(Robot r : donnees.getRobot()) {
			if(r instanceof Drone) {
				robots.add(new Drone(r.getPosition()));
			}
			if(r instanceof RobotRoue) {
				robots.add(new RobotRoue(r.getPosition()));
			}
			if(r instanceof RobotChenille) {
				robots.add(new RobotChenille(r.getPosition()));
			}
			if(r instanceof RobotPatte) {	
				robots.add(new RobotPatte(r.getPosition()));
			}
		}
		this.incendies = new LinkedList<Incendie>();
		for(Incendie inc : donnees.getIncendie()) {
			incendies.add(new Incendie(inc.getLacase(), inc.getNbrLitres()));
		}
	}
	
	public void setGui(GUISimulator gui) {
		this.gui = gui;
	}
	
    /** executer les �v�nements de la date courante et passer aux �v�nements suivants */
	public void next() {
		// Un silmulateur qu'on a implémenté qui ne prend pas en compte le pas du temps, il exécute les évènements selon l'ordre,
		// n'importe le temps qui les sépare
		/*ArrayList<Evenement> evenementsEnCours = null;
		long kk = -1 ;
		for (long k : evenements.keySet()) {
			if (k>=dateSimulation) {
				evenementsEnCours = evenements.get(k);
				kk = k;
				break;
			}
		}
		if(evenementsEnCours != null) {
			for (Evenement evnt: evenementsEnCours) {
				evnt.execute();
			}
		}
		
		if (kk>=dateSimulation) 
			dateSimulation = kk + 1;
		
		else
			dateSimulation += 1;*/
		//if (kk != -1)
		//	evenements.remove(kk);
		
		if(!simulationTerminee()) {
			if(evenements.containsKey(dateSimulation)) {
				for(Evenement e : evenements.get(dateSimulation)) {
					e.execute();
				}
			}
			incrementeDate();
		}
	}
	/**La lecture est alors arr�t�e, et le simulateur doit revenir dans l��tat initial */
	public void restart(){
		dateSimulation = 0;
		LinkedList<Robot> newRobots = new LinkedList<Robot>();
		for(int index = 0; index < robots.size(); index++) {
			donnees.getRobot().get(index).setPosition(robots.get(index).getPosition());
			donnees.getRobot().get(index).setPositionFictive((robots.get(index).getPosition()));
			donnees.getRobot().get(index).setQuantiteEau(robots.get(index).getQuantiteEau());
		}
		
		LinkedList<Incendie> newncendies = new LinkedList<Incendie>();
		for(int index = 0; index < incendies.size(); index++) {
			donnees.getIncendie().get(index).setNbrLitres(incendies.get(index).getNbrLitres());
		}
		
		/*for(long ancienneDate : evenements.keySet()) {
			for (Evenement e : evenements.get(ancienneDate)) {
				e.setDate(ancienneDate + dateSimulation);
			}
			evenements.replace(ancienneDate + dateSimulation, evenements.get(ancienneDate));
		}*/
		
		try {
			donnees.getCarte().drawCarte(donnees, gui);
			donnees.drawincendies(gui);
			donnees.drawrobots(gui);
		} catch (DataFormatException e) {
			// Do nothing actually
			e.printStackTrace();
		}
		
	}
	
	/** ajouter un �v�nement */
	public void ajouteEvenement(Evenement e) {
		if(evenements == null) {
			evenements = new TreeMap<Long, ArrayList<Evenement>>();
		    ArrayList<Evenement> event = new ArrayList<Evenement>();
		    event.add(e);
		    evenements.put(e.getDate(), event);
		}
		if (evenements.containsKey(e.getDate()))
			evenements.get(e.getDate()).add(e);
		else {
			ArrayList<Evenement> newlistEvenement= new ArrayList<Evenement>();
			newlistEvenement.add(e);
			evenements.put(e.getDate(), newlistEvenement);
	
		}
	}
	
	/** incr�mente la date courante puis ex�cute dans l�ordre tous les �v�nements non encore ex�cut�s jusqu��
    cette date */
	private void incrementeDate() {
		dateSimulation ++;	
	}
	
	/** retourne true si plus aucun �v�nement n�est en attente d�ex�cution*/
	private boolean simulationTerminee() {
		for (long k : evenements.keySet()) {
			if (k >= dateSimulation)
				return false;
			}
		return true;			
	}
}
