package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.exceptions.TowerOccupiedException;

import java.util.ArrayList;

public abstract class ActionSpaceWithEffect extends ActionSpace {

	public void setOccupied(Familiar occupant) {
		super.setOccupied(occupant);
	}

	public boolean isOccupied()  {
		return super.isOccupied();
	}

	private ArrayList<Effect> effect; // this is the bonus directly associated with the action space itself

	@Override
	public abstract ArrayList<Effect> getEffects();

	public abstract void applyEffect();

}
