package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ConnectToServerMessage implements LobbyMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(LobbyMessageVisitor vi) {

    }
}
