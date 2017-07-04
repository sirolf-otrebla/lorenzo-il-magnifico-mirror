package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.interfaces.ActionSpaceViewObject;
import it.polimi.ingsw.ps05.net.message.ActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class MoveFamiliarListener implements Observer {

    public MoveFamiliarListener(){
    }

    @Override
    public void update(Observable o, Object arg) {

        ActionSpaceViewObject spaceWidget = (ActionSpaceViewObject) arg;
        ActionMessage msg =
                new ActionMessage( spaceWidget.getFamilyMemberID(), spaceWidget.getId(), 0, null); //todo
        Client.getInstance().sendToServer(msg);

    }
}
