package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;

public class Familiar {

	private ActionSpace position;
	Dice relatedDice;
	Color color;
	private Player relatedPlayer;

	public Dice  getRelatedDice(){

		return relatedDice;
	}

	public Player getRelatedPlayer() {
		return relatedPlayer;
	}

	public ActionSpace getPosition() {
		return position;
	}

	public boolean isUsed(){
		if (position != null) return true;
		return false;
	}


	public void setPosition(ActionSpace position) {
		this.position = position;
	}
}
