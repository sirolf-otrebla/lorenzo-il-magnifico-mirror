package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public interface Resource {

	// public int getResourceValue();
	
	// public int getDescription();
	
	// what other?

    public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException;

    public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException;

    // in this case we use familiar as a reference to the player, because of resources (e.g. dices) who need also a familiar.
    public void removeFromPlayer(Familiar playerFamiliar) throws NotEnoughResourcesException, DiceTooLowException;

    public boolean hasEnoughResources(Familiar playerFamiliar);
	
}
