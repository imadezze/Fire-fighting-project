package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import evenement.Evenement;
import simulation.Case;

public class Graphe {
	  private ArrayList<Case> listCases;
	  private Sommet[] Sommets;
	  private int noOfSommets;
	  private Arete[] Aretes;
	  private int noOfAretes;
	 
	  public Sommet[] getSommets() {
		  return Sommets;
	  }
	  
	  public ArrayList<Case> getListCases() {
			return listCases;
		}
	  
	  public Graphe(Arete[] Aretes, ArrayList<Case> listCases) {
	    this.Aretes = Aretes;
	    this.listCases = listCases;
	 
	    // create all Sommets ready to be updated with the Aretes
	    this.noOfSommets = calculateNoOfSommets(Aretes);
	    this.Sommets = new Sommet[this.noOfSommets];
	 
	    for (int n = 0; n < this.noOfSommets; n++) {
	      this.Sommets[n] = new Sommet();
	    }
	 
	    // add all the Aretes to the Sommets, each Arete added to two Sommets (to and from)
	    this.noOfAretes = Aretes.length;
	 
	    for (int AreteToAdd = 0; AreteToAdd < this.noOfAretes; AreteToAdd++) {
	      this.Sommets[Aretes[AreteToAdd].getFromSommetIndex()].getAretes().add(Aretes[AreteToAdd]);
		  this.Sommets[Aretes[AreteToAdd].getFromSommetIndex()].setLacase(listCases.get(Aretes[AreteToAdd].getFromSommetIndex()));
		  listCases.get(Aretes[AreteToAdd].getFromSommetIndex()).setSommet(this.Sommets[Aretes[AreteToAdd].getFromSommetIndex()]);
	      this.Sommets[Aretes[AreteToAdd].getToSommetIndex()].getAretes().add(Aretes[AreteToAdd]);
		  this.Sommets[Aretes[AreteToAdd].getToSommetIndex()].setLacase(listCases.get(Aretes[AreteToAdd].getToSommetIndex()));
		  listCases.get(Aretes[AreteToAdd].getToSommetIndex()).setSommet(this.Sommets[Aretes[AreteToAdd].getToSommetIndex()]);
	    }
	 
	  }
	 
	  private int calculateNoOfSommets(Arete[] Aretes) {
	    int noOfSommets = 0;
	 
	    for (Arete e : Aretes) {
	      if (e.getToSommetIndex() > noOfSommets)
	        noOfSommets = e.getToSommetIndex();
	      if (e.getFromSommetIndex() > noOfSommets)
	        noOfSommets = e.getFromSommetIndex();
	    }
	 
	    noOfSommets++;
	 
	    return noOfSommets;
	  }
	  
	  // next video to implement the Dijkstra algorithm !!!
	  public Map<Sommet, ArrayList<Sommet>> shortestDistancesAndPath(int index) {
		
		//chemin optimale a retourner  
		Map<Sommet, ArrayList<Sommet>> chemin = new HashMap<Sommet, ArrayList<Sommet>>();
		ArrayList<Sommet> firstPath = new ArrayList<Sommet>();
		firstPath.add(Sommets[index]);
		chemin.put(Sommets[index], firstPath);
		
		// reinitialisation du graphe, et distance
		for (int i = 0; i < this.Sommets.length; i++) {	
			Sommets[i].setDistanceFromSource(Double.POSITIVE_INFINITY);
			Sommets[i].setVisited(false);
		}
		
		// Sommet index as source
	    this.Sommets[index].setDistanceFromSource(0);
	    int nextSommet = index;
	 
	    // visit every Sommet
	    for (int i = 0; i < this.Sommets.length; i++) {	
		  // loop around the Aretes of current Sommet
	      ArrayList<Arete> currentSommetAretes = this.Sommets[nextSommet].getAretes();
	 
	      for (int joinedArete = 0; joinedArete < currentSommetAretes.size(); joinedArete++) {
	        int neighbourIndex = currentSommetAretes.get(joinedArete).getNeighbourIndex(nextSommet);
	 
	        // only if not visited
	        if (!this.Sommets[neighbourIndex].isVisited()) {
	          double tentative = this.Sommets[nextSommet].getDistanceFromSource() + currentSommetAretes.get(joinedArete).getLength();
	 
	          if (tentative < Sommets[neighbourIndex].getDistanceFromSource()) {
	            Sommets[neighbourIndex].setDistanceFromSource(tentative);
	            
	            // mise à jour du chemin optimale
	            ArrayList<Sommet> path = new ArrayList<>(chemin.get(Sommets[nextSommet]));
	            path.add(Sommets[neighbourIndex]);
	            
	            chemin.put(Sommets[neighbourIndex], path);
	          }
	        }
	      }
	 
	      // all neighbours checked so Sommet visited
	      Sommets[nextSommet].setVisited(true);
	      
	      // next Sommet must be with shortest distance
	      nextSommet = getSommetShortestDistanced(index);
	      
	   }
	    
	    // renvoie l'arboresence des plus courts chemins
	    return chemin;
	    
	  }
	 
	  // now we're going to implement this method in next part !
	  private int getSommetShortestDistanced(int index) {
	    int storedSommetIndex = index;
	    double storedDist = Double.POSITIVE_INFINITY;
	 
	    for (int i = 0; i < this.Sommets.length; i++) {
	      double currentDist = this.Sommets[i].getDistanceFromSource();
	 
	      if (!this.Sommets[i].isVisited() && currentDist < storedDist) {
	        storedDist = currentDist;
	        storedSommetIndex = i;
	      }
	    }
	 
	    return storedSommetIndex;
	  }
}
