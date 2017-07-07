package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 07/07/2017.
 */
public class BonusHarvestTriggerMessage implements GameResponseMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
         vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameResponseMessageVisitor vi) {
    }
}
