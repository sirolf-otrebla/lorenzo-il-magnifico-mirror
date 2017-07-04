package it.polimi.ingsw.ps05.model.spaces;

import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.Familiar;

import java.util.ArrayList;

public abstract class ActionSpaceWithEffect extends ActionSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6346094062227140134L;

	@Override
	public abstract ArrayList<Effect> getEffects() throws IllegalMethodCallException;

	public abstract void applyEffect(Familiar pl);

}
