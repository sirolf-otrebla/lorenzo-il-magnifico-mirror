package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.message.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.net.server.PlayerClient;
import it.polimi.ingsw.ps05.resourcesandbonuses.BonusAction;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 12/06/2017.
 */

public class BonusActionListener implements Observer {

    private ResultTriggerVisitor visitor;
    private Round round;

    public BonusActionListener(Round round){
        this.visitor = new ResultTriggerVisitor();
        this.round = round;
    }
    @Override
    public void update(Observable o, Object arg) {
        //TODO: specificare i messaggi
        BonusActionTriggerMessage mess = new BonusActionTriggerMessage();
        PlayerClient client = round.getGame().getPlayerInGame().get(
                round.getActivePlayer().getPlayerID());
        client.sendMessage(mess);
        round.getGame().setActivePlayer(this.round.getActivePlayer());
    }

    public ResultTriggerVisitor getVisitor() {
        return visitor;
    }
}
