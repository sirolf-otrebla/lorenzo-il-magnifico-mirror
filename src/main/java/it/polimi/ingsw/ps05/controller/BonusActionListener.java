package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.message.BonusActionTriggerMessage;
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
    private GameFlowController gfc;

    public BonusActionListener(GameFlowController gfc){
        this.visitor = new ResultTriggerVisitor();
        this.gfc = gfc;
    }
    @Override
    public void update(Observable o, Object arg) {
        //TODO: specificare i messaggi
        BonusActionTriggerMessage mess = new BonusActionTriggerMessage();
        for (PlayerClient client:
                gfc.getGame().getPlayerInGame()) {
            if (client.getPlayer() == gfc.activePlayer)
                client.sendMessage(mess);
        }



    }

    public ResultTriggerVisitor getVisitor() {
        return visitor;
    }
}
