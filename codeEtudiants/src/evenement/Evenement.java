package evenement;
/**  class mere de l'evenement */

public abstract class Evenement{
    /** La date de l'évènement */
    private long date;

    /** Créer l'évènement: Constructeur
     * @param date */
    public Evenement(long date){
        this.date = date;
    }

    /** getter la date */
    public long getDate(){
        return date;
    }

    public void setDate(long date) {
    	this.date = date;
    }
    
    /** Une méthode abstraite pour éxecuter l'évènement */
    public abstract void execute();
} 