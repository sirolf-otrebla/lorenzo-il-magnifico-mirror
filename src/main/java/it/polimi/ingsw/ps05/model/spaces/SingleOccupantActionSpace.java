package it.polimi.ingsw.ps05.model.spaces;

import it.polimi.ingsw.ps05.model.Familiar;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class SingleOccupantActionSpace extends ActionSpaceWithEffect {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6904353010062127223L;
	private Familiar occupant;

    public Familiar getOccupant(){
        return super.getOccupant();
    }


    public void setOccupant(Familiar firstOccupant) {
    	super.setOccupied(firstOccupant);
    }
}
