package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.client.view.gui.ActionSpaceWidget;
import it.polimi.ingsw.ps05.model.Action;
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

        ActionSpaceWidget spaceWidget = (ActionSpaceWidget) arg;
        ActionMessage msg =
                new ActionMessage( spaceWidget.getFamilyMemberID(), spaceWidget.getId(), 0);
        Client.getInstance().sendToServer(msg);
        Client.getInstance().waitUpdate();
    }
}
