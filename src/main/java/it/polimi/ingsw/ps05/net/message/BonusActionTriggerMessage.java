package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 12/06/2017.
 */
public class BonusActionTriggerMessage implements GameMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
