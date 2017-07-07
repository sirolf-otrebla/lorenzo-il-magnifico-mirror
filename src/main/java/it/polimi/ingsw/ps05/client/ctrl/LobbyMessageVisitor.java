package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.EnteringLobbyMessage;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.RejoinMessage;

import java.util.ArrayList;

/**
 * Created by Alberto on 03/07/2017.
 */
public class LobbyMessageVisitor {

	public LobbyMessageVisitor(){


	}

	public void visit(EnteringLobbyMessage lobbyMessage){
		if (lobbyMessage.isYouOrOthers()) ;
		System.out.println("sono nel secondo visitor ( messaggio EnteringLobbyMessage)");
		ArrayList<String> usernames = lobbyMessage.getPlayerUsernamesList();
		Client.getInstance().getLoginController().setLobby();
		Client.getInstance().getLoginController().setLobbyUsernames(lobbyMessage.getPlayerUsernamesList());
	}

	public void visit(GameSetupMessage gameSetupMessage){
		System.out.println("sono nel secondo visitor ( messaggio GameSetupMessage)");
		String viewType;
		try{
			viewType = Client.getInstance().getLoginController().getViewType().toLowerCase();
		} catch (NullPointerException e){
			viewType = "cli";
		}

		Client.getInstance().startGame(viewType, gameSetupMessage);

	}

	public void visit(RejoinMessage rejoinMessage){
		this.visit(rejoinMessage.gameSetupMessage());
		System.out.println("Primo visitor, GameUpdateMsg");
		Client.getInstance().setGameStatus(rejoinMessage.gameUpdateMessage().getGameStatus());
		System.out.println("Settato game status? " + Client.getInstance().getGameStatus());
		System.out.println("C'è player? " +  Client.getInstance().getGameStatus() == null ? "null" :  Client.getInstance().getGameStatus().getThisPlayer());
		ViewAdapter.getInstance().startGameView(rejoinMessage.gameUpdateMessage().getGameStatus());
	}
}
