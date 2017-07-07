package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class RejoinMessage implements LobbyMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2092487251464388365L;
	GameSetupMessage setup;
	GameUpdateMessage update;

	public RejoinMessage(GameSetupMessage setup, GameUpdateMessage update){
		this.setup = setup;
		this.update = update;
	}

	@Override
	public void acceptVisitor(NetMessageVisitor vi) throws Exception {
		vi.visit(this);

	}

	@Override
	public void acceptVisitor(LobbyMessageVisitor vi) {
		vi.visit(this);

	}
	
	public GameSetupMessage gameSetupMessage(){
		return this.setup;
	}
	
	public GameUpdateMessage gameUpdateMessage(){
		return this.update;
	}

}
