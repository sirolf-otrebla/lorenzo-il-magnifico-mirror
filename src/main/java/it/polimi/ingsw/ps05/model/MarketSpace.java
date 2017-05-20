package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

/* TODO: write UMLs methods and attributes --Sirolfo */

public class MarketSpace extends ActionSpaceWithEffect {
	
	ArrayList<ActionResult> effectsOnPositioning;
	
	public MarketSpace(){
		super();
	}
	
	public MarketSpace(ArrayList<ActionResult> effectsOnPositioning){
		super();
		this.effectsOnPositioning = effectsOnPositioning;
	}
	
	public MarketSpace(Integer diceRequired, ArrayList<ActionResult> effectsOnPositioning){
		super();
		super.setDiceRequirement(diceRequired);
		this.effectsOnPositioning = effectsOnPositioning;
	}
	

}
