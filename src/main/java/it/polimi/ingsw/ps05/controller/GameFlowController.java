package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.net.server.Message.NetMessage;

public class GameFlowController implements Runnable{
	
	Turn currentTurn;
	Player activePlayer;
	private NetMessage gameInput = null;
	private Game game;
	private TurnSetupManager tManager;

	public GameFlowController(Game game){
		this.game = game;
		tManager = new TurnSetupManager()

	}

	public NetMessage getGameInput() {

		return gameInput;

	}

	public synchronized void setGameInput(NetMessage gameInput) {
		this.gameInput = gameInput;
		notifyAll();
	}

	@Override
	public void run() {
		while(game.end){

		}
		if (game.end){

		}
	}
}
