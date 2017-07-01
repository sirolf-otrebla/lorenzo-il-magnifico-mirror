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

    private ArrayList<Familiar> occupantList = new ArrayList<Familiar>();

    public ArrayList<Familiar> getOccupantList(){
        return this.occupantList;
    }

}
