package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class EnterGameMessage implements LobbyMessage{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9067574575230338819L;

	@Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(LobbyMessageVisitor vi) {
        // vi.visit(this);
    }
}
