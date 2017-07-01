package it.polimi.ingsw.ps05.model.spaces;

import it.polimi.ingsw.ps05.client.ctrl.UpdateViewVisitor;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;

import java.util.ArrayList;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class MultipleOccupantsActionSpace extends ActionSpaceWithEffect {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8263755224788573779L;

	public ArrayList<Familiar> getOccupantList(){
    	ArrayList<Familiar> list = new ArrayList<>();
    	
    	list.add(super.getOccupant());
    	list.addAll(super.getAdditionalOccupant());
    	
        return list;
    }

}
