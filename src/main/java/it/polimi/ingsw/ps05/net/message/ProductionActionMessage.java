package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ProductionActionMessage implements GameMessage {

    private ActionMessage actionMessage;
    private ArrayList<Integer> activeCardsIds;

    public ProductionActionMessage(ActionMessage msg, ArrayList<Integer> activeCardsIds){
        this.actionMessage = msg;
        this.activeCardsIds = activeCardsIds;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    public ActionMessage getActionMessage() {
        return actionMessage;
    }

    public ArrayList<Integer> getActiveCardsIds() {
        return activeCardsIds;
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
        vi.visit(this);
    }
}
