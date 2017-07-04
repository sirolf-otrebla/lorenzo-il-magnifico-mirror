package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.LobbyResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 04/07/2017.
 */
public class SetupDoneMessage implements LobbyResponseMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(LobbyResponseMessageVisitor vi) {
        vi.visit(this);
    }
}
