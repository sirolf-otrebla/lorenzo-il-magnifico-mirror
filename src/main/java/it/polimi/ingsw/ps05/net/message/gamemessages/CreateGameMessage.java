package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class CreateGameMessage implements GameMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1409055642313205440L;

	@Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
