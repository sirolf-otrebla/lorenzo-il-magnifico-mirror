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

public class GameFlowController implements Runnable, NetMessageVisitor {
	
	Turn currentTurn;
	Player activePlayer;
	private NetMessage gameInput = null;
	private Game game;
	private BonusActionListener bonusActListener;
	private Iterator<Player> plOrdIt;
	private ExcommunicationTriggerListener exTrigger;

	public GameFlowController(Game game){
		exTrigger = new ExcommunicationTriggerListener(this);
		this.bonusActListener = new BonusActionListener(this);
		this.game = game;
		this.game.gettManager().addObserver(exTrigger);
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
				this.activePlayer = thisTurn.getPlayerOrder().get(0);
				ArrayList<Player> PlayerOrder = thisTurn.getPlayerOrder();
				plOrdIt = PlayerOrder.iterator();

				while (plOrdIt.hasNext()) {
					this.activePlayer = plOrdIt.next();
					evaluatePermanentEffect();
					NetMessage inputMessage = this.getInput();
					inputMessage.acceptVisitor(this);
				}


				if (turnFinished) this.game.gettManager().loadNextTurn();

			} catch (InterruptedException e ){

			} catch (Exception f){

			}
		}
		if (game.end){

		}
	}


	private void evaluatePermanentEffect(){
		for (PermanentBonus e :
				this.activePlayer.getPermanentBonusList()) {
			e.applyResult(this.activePlayer);
		}
	}

	private void resetEffects(){
		for (PermanentBonus e :
				this.activePlayer.getPermanentBonusList()){
			e.resetResult(this.activePlayer);
		}
	}

	private  NetMessage getInput() throws InterruptedException{
		if (this.gameInput == null) wait();
		return  gameInput;
	}

	public BonusActionListener getBonusActListener() {
		return bonusActListener;
	}

	public Game getGame(){
		return game;
	}

	public void setNextPlayer(){
		this.activePlayer.resetPermanentEffects();
		activePlayer = plOrdIt.next();
	}

	private void evaluateVictoryPts(Player pl){

	}


	@Override
	public void visit(ActionMessage mess) {
		try {
			Player pl = mess.getPlayerBefore();
			validatePlayer(pl);
			Player thisPl = this.activePlayer;
			Action act = thisPl.doAction(mess.getFamiliar(),
					mess.getActionSpace(), mess.getSelectedPayment());
			UpdateMessage updateMsg = new UpdateMessage(act, thisPl, this.activePlayer);
			for (PlayerClient client :
					game.getPlayerInGame()) {
				client.sendMessage(updateMsg);
			}
		} catch (Exception e){
			//TODO:
		}


	}

	@Override
	public void visit(ExitGameMessage mess){
		//TODO:
		// gestione permanenza partita
	}

	public void visit(LeaderCardMessage lCardMsg){
		try {
			LeaderCard card =
					this.activePlayer.getLeaderCard(lCardMsg.getLeaderCard().getName());
			if (lCardMsg.getMsgType() == LeaderCardMessage.TYPE_DISCARD)
				card.discard(this.activePlayer);
			else{
				card.applyNonActivableEffects(activePlayer, lCardMsg.getEffectChoice());
				this.activePlayer.resetPermanentEffects();
				this.evaluatePermanentEffect();

			}
		} catch (MissingCardException e){
			//TODO:
		}
	}

}
