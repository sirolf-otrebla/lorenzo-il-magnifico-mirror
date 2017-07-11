package it.polimi.ingsw.ps05.net.message;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameResponseMessage;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class EndGameMessage implements GameResponseMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 452184660042919448L;
	Player[] list;
	
	public EndGameMessage(Player[] list) {
		this.list = list;
	}
	
	@Override
	public void acceptVisitor(NetMessageVisitor vi) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptVisitor(GameResponseMessageVisitor vi) {
		vi.visit(this);
	}
	
	@Override
	public String toString(){
		String n  = "Classifica:\n";
		for (int i = 0; i < list.length; i++){
			n = n + (i+1) + ") " + list[i] + "\n";
		}
		return n;
	}
	
	

}
