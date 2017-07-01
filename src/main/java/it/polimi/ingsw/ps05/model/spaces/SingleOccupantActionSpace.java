package it.polimi.ingsw.ps05.model.spaces;

import it.polimi.ingsw.ps05.model.Familiar;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class SingleOccupantActionSpace extends ActionSpaceWithEffect {

    private Familiar occupant;

    public Familiar getOccupant(){
        return occupant;
    }


    public void setOccupant(Familiar firstOccupant) {
        this.occupant = firstOccupant;
    }
}
