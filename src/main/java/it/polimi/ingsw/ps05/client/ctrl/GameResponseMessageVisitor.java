package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.net.message.GameUpdateMessage;

/**
 * Created by Alberto on 07/07/2017.
 */
public class GameResponseMessageVisitor {

	public void visit(GameUpdateMessage msg){
		System.out.println("Primo visitor, GameUpdateMsg");
		Client.getInstance().setGameStatus(msg.getGameStatus());
		System.out.println("Settato game status? " + Client.getInstance().getGameStatus());
		System.out.println("C'è player? " +  Client.getInstance().getGameStatus() == null ? "null" :  Client.getInstance().getGameStatus().getThisPlayer());
		System.out.println("Game: " + Client.getInstance().isInGame());
		if (Client.getInstance().isInGame()){
			System.out.println("Update");
			ViewAdapter.getInstance().updateView(msg.getGameStatus());
		}else {
			System.out.println("Start");
			ViewAdapter.getInstance().startGameView(msg.getGameStatus());
		}

	}

	public void visit(BonusActionTriggerMessage msg){
		this.visit(msg.getGameUpdateMessage());
		if (Client.getInstance().isInGame()){
			ViewAdapter.getInstance().setGhostFamiliarForAction(msg.getGhostFamiliar());
		}
	}
}
