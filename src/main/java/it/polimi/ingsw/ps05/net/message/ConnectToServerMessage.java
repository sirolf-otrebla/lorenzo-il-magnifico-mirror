package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ConnectToServerMessage implements LobbyMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = -311143099125814840L;

	@Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(LobbyMessageVisitor vi) {

    }
}
