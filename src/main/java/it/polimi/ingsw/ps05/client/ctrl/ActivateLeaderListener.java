package it.polimi.ingsw.ps05.client.ctrl;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps05.client.view.interfaces.ActivateLeaderViewObject;
import it.polimi.ingsw.ps05.net.message.gamemessages.ActivateLeaderMessage;

/**
 * Created by Alberto on 26/06/2017.
 */
public class ActivateLeaderListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {

    	 ActivateLeaderViewObject leaderWidget = (ActivateLeaderViewObject) arg;
         ActivateLeaderMessage msg = new ActivateLeaderMessage(
                  leaderWidget.getReferenceID(), Client.getInstance().getGameStatus().getThisPlayer());
         Client.getInstance().sendToServer(msg);
    }
}
