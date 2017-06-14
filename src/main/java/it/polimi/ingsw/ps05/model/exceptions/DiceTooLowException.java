package it.polimi.ingsw.ps05.model.exceptions;

import it.polimi.ingsw.ps05.resourcesandbonuses.ServantResource;

/**
 * Created by Alberto on 18/05/2017.
 */
public class DiceTooLowException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ServantResource servantsNeeded;

    public DiceTooLowException(int servantsNeeded){
        this.servantsNeeded = new ServantResource(servantsNeeded);
    }
}
