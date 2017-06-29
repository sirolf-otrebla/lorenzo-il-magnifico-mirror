package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 12/06/2017.
 */

public class BonusActionListener implements Observer {

    private ResultTriggerVisitor visitor;
    private GameFlowController gfc;

    public BonusActionListener(GameFlowController gfc){
        this.visitor = new ResultTriggerVisitor();
        this.gfc = gfc;
    }
    @Override
    public void update(Observable o, Object arg) {
        //TODO: specificare i messaggi
        BonusActionTriggerMessage mess = new BonusActionTriggerMessage();
        PlayerClient client = gfc.getGame().getPlayerInGame().get(
                gfc.getGame().getActivePlayer().getPlayerID());
        client.sendMessage(mess);
        // inutile
        gfc.getGame().setActivePlayer(this.gfc.getGame().getActivePlayer());
        this.gfc.sendUpdateMsg();

    }

    public ResultTriggerVisitor getVisitor() {
        return visitor;
    }
}
