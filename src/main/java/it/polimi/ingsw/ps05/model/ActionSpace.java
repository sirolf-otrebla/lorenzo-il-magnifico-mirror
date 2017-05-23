package it.polimi.ingsw.ps05.model;


import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import java.util.ArrayList;

/* represent the generalization of all familiar-usable spaces within the game board
 * 
 * further comments will be added.
 */
public abstract class ActionSpace {

	private boolean isOccupied;
	private Integer diceRequirement;
	private final static Integer defaultDiceRequired = 1; //le sotto classi lo usano in un costruttore in cui si passa solo l'effetto, può essere comodo
	private ArrayList<ArrayList<Resource>> requirements;

	public ActionSpace() {
		diceRequirement = defaultDiceRequired;
	}

	private Familiar occupant;

	public Familiar getOccupant(){
		return occupant;
	}

	public void setOccupied(Familiar occupant) {
		this.occupant = occupant;
		isOccupied = true;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public abstract ArrayList<Effect> getEffects(); //TODO: implement this method in subclasses;

	public  ArrayList<ArrayList<Resource>> getRequirements(){
		return requirements;
	}
	
	protected void setDiceRequirement(Integer diceRequirement){
		this.diceRequirement = diceRequirement;
	}

	public Integer getDiceRequirement() {
		return diceRequirement;
	}

	public void setOccupant(Familiar occupant) {
		this.occupant = occupant;
	}
	
	public void reset(){
		isOccupied = false;
		occupant = null;
	}
	
	


	public abstract void applyEffect();
}
