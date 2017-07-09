package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public class PassActionMessage implements GameMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2378409910970112871L;

	@Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
