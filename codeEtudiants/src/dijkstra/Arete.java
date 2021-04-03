package dijkstra;

public class Arete {
	private int fromSommetIndex;
	  private int toSommetIndex;
	  private double length;
	 
	  public Arete(int fromSommetIndex, int toSommetIndex, double length) {
	    this.fromSommetIndex = fromSommetIndex;
	    this.toSommetIndex = toSommetIndex;
	    this.length = length;
	  }
	 
	  public int getFromSommetIndex() {
	    return fromSommetIndex;
	  }
	 
	  public int getToSommetIndex() {
	    return toSommetIndex;
	  }
	 
	  public double getLength() {
	    return length;
	  }
	 
	  // determines the neighbouring Sommet of a supplied Sommet, based on the two Sommets connected by this edge
	  public int getNeighbourIndex(int SommetIndex) {
	    if (this.fromSommetIndex == SommetIndex) {
	      return this.toSommetIndex;
	    } else {
	      return this.fromSommetIndex;
	   }
	  }
}
