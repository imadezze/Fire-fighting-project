package dijkstra;

import java.util.ArrayList;

import simulation.Case;

public class Sommet {
	  
	  private double distanceFromSource = Double.POSITIVE_INFINITY;
	  private boolean visited;
	  private Case lacase;
	  private ArrayList<Arete> Aretes = new ArrayList<Arete>(); // now we must create Aretes
	 
	  public Case getLacase() {
			return lacase;
		}

		public void setLacase(Case lacase) {
			this.lacase = lacase;
		}
	  public double getDistanceFromSource() {
	    return distanceFromSource;
	  }
	 
	  public void setDistanceFromSource(double distanceFromSource) {
	    this.distanceFromSource = distanceFromSource;
	  }
	 
	  public boolean isVisited() {
	    return visited;
	  }
	 
	  public void setVisited(boolean visited) {
	    this.visited = visited;
	  }
	 
	  public ArrayList<Arete> getAretes() {
	    return Aretes;
	  }
	 
	  public void setAretes(ArrayList<Arete> Aretes) {
	    this.Aretes = Aretes;
	  }
	  
}
