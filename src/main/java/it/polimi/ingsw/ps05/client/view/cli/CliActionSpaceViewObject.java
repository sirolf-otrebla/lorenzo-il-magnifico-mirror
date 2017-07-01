package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.ActionSpaceViewObject;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;

import java.util.Observable;

/**
 * Created by Alberto on 01/07/2017.
 */
public class CliActionSpaceViewObject extends ActionSpaceViewObject {
    private ActionSpace actionSpace;
    private ColorEnumeration familyMemberID;

    public CliActionSpaceViewObject(ActionSpace space, ColorEnumeration familyMemberID){
        this.actionSpace = space;
        this.familyMemberID = familyMemberID;
        Client.getInstance().linkToObserver(this);
    }

    @Override
    public ColorEnumeration getFamilyMemberID() {
        return this.familyMemberID;
    }

    @Override
    public Integer getId() {
        return this.actionSpace.getId();
    }

    @Override
    public void notifyObservers(){
        super.notifyObservers(this);
    }
}
