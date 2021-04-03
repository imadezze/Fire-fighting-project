package simulation;

import dijkstra.*;

/** une case, l'unite elementaire d'une carte */
public class Case {
	
	/** numero de la ligne de la case */
	private int ligne;
	
	/** numero de la colonne de la case */
	private int colonne;
	
	/** nature du terrain de la case */
	private NatureTerrain nature;
	
	/** le sommet associe a la case dans le graphe*/
	private Sommet sommet;
	
	public Sommet getSommet(){
		return sommet;
	}
	
	public void setSommet(Sommet sommet){
		this.sommet = sommet;
	}
	
	public Case(int ligne, int colonne, NatureTerrain nature) {
		this.colonne = colonne;
		this.ligne = ligne;
		this.nature = nature;
	}
	
	public int getLigne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	public NatureTerrain getNature() {
		return nature;
	}
}
