package it.polimi.ingsw.ps05.model.exceptions;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ServantResource;

/**
 * Created by Alberto on 18/05/2017.
 */
public class DiceTooLowException extends Exception {

    public ServantResource servantsNeeded;

    public DiceTooLowException(int servantsNeeded){
        this.servantsNeeded = new ServantResource(servantsNeeded);
    }
}
