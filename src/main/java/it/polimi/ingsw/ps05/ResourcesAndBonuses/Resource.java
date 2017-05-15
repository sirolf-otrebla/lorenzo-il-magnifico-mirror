package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public interface Resource {

	// public int getResourceValue();
	
	// public int getDescription();
	
	// what other?

    public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException;

    public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException;
	
}
