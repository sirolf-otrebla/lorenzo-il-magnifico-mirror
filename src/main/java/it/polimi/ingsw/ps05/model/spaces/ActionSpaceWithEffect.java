package it.polimi.ingsw.ps05.model.spaces;

import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.Familiar;

import java.util.ArrayList;

public abstract class ActionSpaceWithEffect extends ActionSpace {

	public void setOccupied(Familiar occupant) {
		super.setOccupied(occupant);
	}

	public boolean isOccupied()  {
		return super.isOccupied();
	}

	//private ArrayList<Effect> effect; // this is the bonus directly associated with the action space itself

	@Override
	public abstract ArrayList<Effect> getEffects();

	public abstract void applyEffect();

}
