package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import sun.nio.ch.Net;

/**
 * Created by Alberto on 29/06/2017.
 */
public class PassActionMessage implements GameMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
