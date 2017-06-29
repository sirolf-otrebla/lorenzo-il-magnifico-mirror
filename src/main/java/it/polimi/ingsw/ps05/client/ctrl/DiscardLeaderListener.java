package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.LeaderWidget;
import it.polimi.ingsw.ps05.net.message.DiscardLeaderMessage;
import it.polimi.ingsw.ps05.net.message.LeaderCardMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class DiscardLeaderListener implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        LeaderWidget leaderWidget = (LeaderWidget) arg;
        DiscardLeaderMessage msg = new DiscardLeaderMessage(
                 leaderWidget.getReferenceID(), Client.getInstance().getGameStatus().getThisPlayer());
        Client.getInstance().sendToServer(msg);
    }
}
