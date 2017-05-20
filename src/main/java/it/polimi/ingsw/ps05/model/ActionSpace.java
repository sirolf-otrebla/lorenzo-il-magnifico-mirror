package it.polimi.ingsw.ps05.model;


import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.TowerOccupiedException;

import java.util.ArrayList;

/* represent the generalization of all familiar-usable spaces within the game board
 * 
 * further comments will be added.
 */
public abstract class ActionSpace {

	private boolean isOccupied;
	private int DiceRequirement;

	private ArrayList<ArrayList<Resource>> requirements;


	private Familiar occupant;

	public Familiar getOccupant(){
		return occupant;
	}

	public void setOccupied(Familiar occupant) {
		this.occupant = occupant;
		isOccupied = true;
	}

	public boolean isOccupied() throws  TowerOccupiedException{

		return isOccupied;
	}

	public abstract ArrayList<Effect> getEffects(); //TODO: implement this method in subclasses;

	public  ArrayList<ArrayList<Resource>> getRequirements(){
		return requirements;
	}


	public abstract void applyEffect();
}
