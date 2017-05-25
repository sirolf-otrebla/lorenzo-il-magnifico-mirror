package it.polimi.ingsw.ps05.model;

/* todo: add methods defined in UML  --Sirolfo*/

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;

public class CouncilSpace extends ActionSpaceWithEffect {

	private ArrayList<Familiar> occupantList;

	ArrayList<ActionResult> effectsOnPositioning;

	public CouncilSpace() {
		super();
	}

	public CouncilSpace(ArrayList<ActionResult> effectsOnPositioning) {
		super();
		this.effectsOnPositioning = effectsOnPositioning;
	}

	public CouncilSpace(Dice diceRequired, ArrayList<ActionResult> effectsOnPositioning) {
		super();
		super.setDiceRequirement(diceRequired);
		this.effectsOnPositioning = effectsOnPositioning;
	}

	@Override
	public void setOccupied(Familiar occupant) {
		occupantList.add(occupant);
	}

	@Override
	public ArrayList<Effect> getEffects() {
		return null;
	}

	@Override
	public void applyEffect() {

	}

	public void getOrder() {
		// TODO
	}

}
