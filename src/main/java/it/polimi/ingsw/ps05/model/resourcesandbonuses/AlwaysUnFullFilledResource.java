package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class AlwaysUnFullFilledResource implements Resource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7673964039019243366L;
	public static final String id = "Risorsa sempre falsa";

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		throw new IllegalMethodCallException();
	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		throw new IllegalMethodCallException();
	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) throws NotEnoughResourcesException, DiceTooLowException {
		throw new NotEnoughResourcesException();
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return false;
	}

	@Override
	public void setValue(Integer amount) {

	}

	@Override
	public Integer getValue() {
		return null;
	}

	@Override
	public String getID() {
		return id;
	}

}
