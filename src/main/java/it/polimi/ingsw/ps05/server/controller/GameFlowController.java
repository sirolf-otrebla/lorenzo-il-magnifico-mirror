package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.Iterator;

public class GameFlowController implements Runnable {


	private NetMessage gameInput = null;
	private Game game;

	public BonusActionListener bonusActListener;
	public ExcommunicationTriggerListener exTrigger;
	public EndActionListener endActionListener;
	public LimitedBonusActListener limitedBonusActListener;


	public GameFlowController(Game game){
        this.game = game;
        this.exTrigger = new ExcommunicationTriggerListener(this);
		this.endActionListener = new EndActionListener(this);
		this.bonusActListener = new BonusActionListener(this);
		this.limitedBonusActListener = new LimitedBonusActListener(this);
	}

	public synchronized void setGameInput(NetMessage gameInput) {
		this.gameInput = gameInput;
		notifyAll();
	}

	@Override
	public void run()  {
		while(game.end){
			try {

				Turn thisTurn = this.game.gettManager().getTurn();
				PlayerClient plClient =
                        game.getPlayerInGame().get(thisTurn.getPlayerOrder().get(0));
				plClient.setActive(true);
				Player c= thisTurn.getPlayerOrder().get(0);
				RoundController turnRoundCtrl = new RoundController(thisTurn, game);
				turnRoundCtrl.executeTurn();
				this.game.gettManager().loadNextTurn();

			} catch (InterruptedException e ){

			} catch (Exception f){

			}
		}
		if (game.end){

		}
	}

	private void evaluateVictoryPts(Player pl){

	}

	public Game getGame() {
		return game;
	}
}
