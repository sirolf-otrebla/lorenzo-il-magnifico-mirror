package it.polimi.ingsw.ps05.model.spaces;

/* todo: add methods defined in UML  --Sirolfo*/

import java.util.*;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.exceptions.CouncilDiceAlreadySet;
import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

public class CouncilSpace extends MultipleOccupantsActionSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3833502039042683186L;


	private ArrayList<Effect> effectList;

	public CouncilSpace() {
		super();
	}

	public CouncilSpace(ArrayList<Effect> effect) {
		super();
		this.effectList = effect;
	}

	public CouncilSpace(Dice diceRequired, ArrayList<Effect> effect) throws RepeatedAssignmentException, CouncilDiceAlreadySet {
		super();

		super.setDiceRequirement(diceRequired);


		if (this.effectList == null) {
			this.effectList = effect;
		} else {
			//TODO: il costruttore deve gestire l'eccezione o propagarla al controller?
			throw new RepeatedAssignmentException();
		}
	}

	@Override
	public void setOccupied(Familiar occupant) {
		super.setOccupied(occupant);;
	}

	@Override
	public ArrayList<Effect> getEffects() {
		return this.effectList;
	}

	@Override
	public void applyEffect(Familiar pl) {
		for (Effect e : getEffects()){
			((SimpleEffect)e).apply(getOccupantList().get(getOccupantList().size()-1));
		}
	}



	public ArrayList<Player> getOrder() {
		boolean alreadyPresent = false;
		ArrayList<Player> playerOrder = new ArrayList<Player>(Board.MAX_PLAYERS);
		Iterator<Player> iter = playerOrder.iterator();

		// adding the first player on the council space
		if(!getOccupantList().isEmpty()) {
			playerOrder.add(getOccupantList().get(0).getRelatedPlayer());
		}

		// loop that fill the playerOrder list (the first place is already set beforehand.
		// checks for multiple familiar for the same player and ignore them
		for(int i = 1; i < playerOrder.size(); i++) {
			while(iter.hasNext() && !alreadyPresent) {
				if(getOccupantList().get(i).getColor() == iter.next().getColor()) {
					alreadyPresent = true;
				}
			}

			// add player if no multiple familiar
			if(!alreadyPresent) {
				playerOrder.add(getOccupantList().get(i).getRelatedPlayer());
			}

			alreadyPresent = false; // reset flag
			iter = playerOrder.iterator(); //reset iterator
		}

		return playerOrder;
	}

	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {

	}

}
