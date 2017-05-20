package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

/* in preliminary UML this object was called ActivitySpace. I decided to change his name because of
 * the possible ambiguity about the term Activity (e.g. there are other objects that uses "Activity"
 * as part of their type name but are also unrelated with this. Moreover, I split the former ActivitySpace
 * in two different classes: this class and HarvestingSpace class. --Sirolfo
 * 
 * further comments will be added
 */
public class ProductionSpace extends ActionSpaceWithEffect {
	
	ArrayList<ActionResult> effectsOnPositioning;

	public ProductionSpace() {
		super();
	}

	public ProductionSpace(ArrayList<ActionResult> effectsOnPositioning) {
		super();
		this.effectsOnPositioning = effectsOnPositioning;
	}

	public ProductionSpace(Integer diceRequired, ArrayList<ActionResult> effectsOnPositioning) {
		super();
		super.setDiceRequirement(diceRequired);
		this.effectsOnPositioning = effectsOnPositioning;
	}
	
	
	
	void startProduce(Familiar familiar){
		
	}

}
