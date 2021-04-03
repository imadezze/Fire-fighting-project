package simulation;

import java.util.LinkedList;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import gui.ImageElement;

public class Incendie {
	
	private int nbrlitres;
	private Case lacase;
	private ImageElement image;
	
	public int getNbrLitres(){
		return nbrlitres;
	}
	public void setNbrLitres(int nbrlitres) {
		this.nbrlitres = nbrlitres;
	}
	public Case getLacase() {
		return lacase;
	}
	public void decrementerlitres(int robotlitres) {
		setNbrLitres(nbrlitres-robotlitres);
	}
	public Incendie(Case la_Case, int nbrlitres){
		this.nbrlitres = nbrlitres;
		this.lacase = la_Case;
	}
	
	/** éteind l'incendie */
	public void eteindre(DonneesSimulation donee) {
		//donee.getIncendie().remove(this);
		/**ImageElement green = new ImageElement(lacase.getColonne()*min, lacase.getLigne()*min, "images/green.jpg", min, min, gui);
        gui.addGraphicalElement(green);*/
		image.translate(-10000, -10000);
	}
	
	/** dessiner les incendies */
	public void draw(Carte carte, GUISimulator gui) throws DataFormatException{
		
		int width = gui.getPanelWidth()/carte.getNbColonnes();
        int heigth = gui.getPanelHeight()/carte.getNblignes();
        int min = heigth;
        if (width <= heigth)
        	min = width;
		
		image = new ImageElement(lacase.getColonne()*min, lacase.getLigne()*min, "images/fire.png", min, min, gui);
        gui.addGraphicalElement(image);
			
	}
}
