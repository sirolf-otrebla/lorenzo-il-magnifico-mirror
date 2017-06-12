package it.polimi.ingsw.ps05.model;

/* todo: add methods defined in UML  --Sirolfo*/

import java.util.*;
import it.polimi.ingsw.ps05.resourcesandbonuses.Dice;

public class CouncilSpace extends ActionSpaceWithEffect {

	private ArrayList<Familiar> occupantList = new ArrayList<Familiar>();

	private ArrayList<Effect> effectList;

	public CouncilSpace() {
		super();
	}

	public CouncilSpace(ArrayList<Effect> effect) {
		super();
		this.effectList = effect;
	}

	public CouncilSpace(Dice diceRequired, ArrayList<Effect> effect) {
		super();
		super.setDiceRequirement(diceRequired);
		this.effectList = effect;
	}

	@Override
	public void setOccupied(Familiar occupant) {
		occupantList.add(occupant);
	}

	@Override
	public ArrayList<Effect> getEffects() {
		return this.effectList;
	}

	@Override
	public void applyEffect() {
		for (Effect e : getEffects()){
			e.apply(occupantList.get(occupantList.size()-1), 0);
		}
	}
	

	public ArrayList<Player> getOrder() {
		boolean alreadyPresent = false;
		ArrayList<Player> playerOrder = new ArrayList<Player>(Board.MAX_PLAYERS);
		Iterator<Player> iter = playerOrder.iterator();

		// adding the first player on the council space
		if(!occupantList.isEmpty()) {
			playerOrder.add(occupantList.get(0).getRelatedPlayer());
		}

		// loop that fill the playerOrder list (the first place is already set beforehand.
		// checks for multiple familiar for the same player and ignore them
		for(int i = 1; i < playerOrder.size(); i++) {
			while(iter.hasNext() && !alreadyPresent) {
				if(occupantList.get(i).getColor() == iter.next().getColor()) {
					alreadyPresent = true;
				}
			}

			// add player if no multiple familiar
			if(!alreadyPresent) {
				playerOrder.add(occupantList.get(i).getRelatedPlayer());
			}

			alreadyPresent = false; // reset flag
			iter = playerOrder.iterator(); //reset iterator
		}

		return playerOrder;
	}

}
