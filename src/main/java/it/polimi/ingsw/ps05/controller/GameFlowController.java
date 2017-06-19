package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.net.server.NetMessageVisitor;
import it.polimi.ingsw.ps05.net.server.PlayerClient;
import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.resourcesandbonuses.PermanentBonus;
import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;

import java.util.ArrayList;
import java.util.Iterator;

public class GameFlowController implements Runnable {


	private NetMessage gameInput = null;
	private Game game;

    private ExcommunicationTriggerListener exTrigger;


    public GameFlowController(Game game){
        this.game = game;
        exTrigger = new ExcommunicationTriggerListener(this);
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


}
