package it.polimi.ingsw.ps05.model.spaces;


import it.polimi.ingsw.ps05.client.view.interfaces.ActionSpaceViewObject;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.VisitableFromView;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.AlwaysUnFullFilledResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

import java.io.Serializable;
import java.util.ArrayList;

/* represent the generalization of all familiar-usable spaces within the game board
 * 
 * further comments will be added.
 */
public abstract class ActionSpace implements Serializable, VisitableFromView{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3124416903238969687L;

	private boolean isOccupied = false;

	//todo: sistemare sta porcata dello static
	// serve per assegnare gli id
	private static int id_counter = 0;
	// this is a REDUNDANT information
	private Dice diceRequirement;
	private final static Integer defaultDiceRequired = 1; //le sotto classi lo usano in un costruttore in cui si passa solo l'effetto, può essere comodo
	private ArrayList<ArrayList<Resource>> requirements;

	private int id;
	private Familiar firstOccupant = null;
	private ArrayList<Familiar> additionalOccupant = new ArrayList<>();



	public ActionSpace() {
		diceRequirement = new Dice(ColorEnumeration.Any, defaultDiceRequired);
		requirements = new ArrayList<>();
		requirements.add(new ArrayList<>());
		requirements.get(0).add(diceRequirement);
		this.id = id_counter;
		id_counter++;
	}


	public void setOccupied(Familiar occupant) {
		if (this.firstOccupant == null){
			this.firstOccupant = occupant;
		} else {
			this.additionalOccupant.add(occupant);
		}
		
		isOccupied = true;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public abstract ArrayList<Effect> getEffects() throws IllegalMethodCallException;

	public ArrayList<ArrayList<Resource>> getRequirements(){
		return requirements;
	}

	public void setDiceRequirement (Dice diceRequirement) {

		// NB: 1ST REQUIREMENT IS <<<<<ALWAYS>>>>> diceRequirement
		this.diceRequirement = diceRequirement;
		for (ArrayList<Resource> a : requirements)
			a.set(0, diceRequirement);
	}

	public Dice getDiceRequirement() {
		return diceRequirement;
	}
	
	public void temporarySetFree(){
		isOccupied = false;
	}
	
	public void temporarySetOccupied(){
		isOccupied = true;
	}

	public void reset(){
		isOccupied = false;
		firstOccupant = null;
		additionalOccupant = new ArrayList<>();
	}

	public void addFalseResource(){
		for (ArrayList<Resource> or : requirements){
			or.add(new AlwaysUnFullFilledResource());
		}
	}

	public void removeFalseResource(){
		for (ArrayList<Resource> or : requirements){
			for (Resource r : or){
				if (r.getID().equals(AlwaysUnFullFilledResource.id)){
					or.remove(r);
				}
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public abstract void applyEffect(Familiar pl);
	
	public Familiar getOccupant(){
		return firstOccupant;
	}
	
	public ArrayList<Familiar> getAdditionalOccupant(){
		return additionalOccupant;
	}

	public ColorEnumeration getFamilyMemberID(){
		if (this.firstOccupant != null)
			return this.firstOccupant.getColor();
		return null;
	}


}
