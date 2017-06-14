package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.UpdateMessage;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.net.server.NetMessageVisitor;
import it.polimi.ingsw.ps05.net.server.PlayerClient;
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
					// definire un metodo clone nel giocatore per
					evaluatePermanentEffect();
					NetMessage inputMessage = this.getInput();
					inputMessage.acceptVisitor(this);

				}
				boolean turnFinished = false;
				for (Familiar f:
					 activePlayer.getFamilyList()) {
					if (!(f.isUsed())) {
						turnFinished = false;
						break;
					}
					turnFinished = true;
				}
				if (turnFinished) {
					this.game.gettManager().loadNextTurn();
				}

			} catch (InterruptedException e ){

			} catch (Exception f){

			}
		}
		if (game.end){

		}
	}


	private void evaluatePermanentEffect(){
		for (PermanentEffect e :
				this.activePlayer.getPermanentEffectList()) {
			e.apply(this.activePlayer, PermanentEffect.NO_ALTERNATIVES );
		}
	}

	private  NetMessage getInput() throws InterruptedException{
		if (this.gameInput == null) wait();
		return  gameInput;
	}

	private void validatePlayer(Player expected) throws  Exception{
		ArrayList<Resource> resList = expected.getResourceList();
		for (Resource res: resList) {
			if(activePlayer.getResource(res.getID()).getValue() != res.getValue())
				throw new Exception();
		}
	}

	public BonusActionListener getBonusActListener() {
		return bonusActListener;
	}

	public Game getGame(){
		return game;
	}

	public void setNextPlayer(){
		activePlayer = plOrdIt.next();
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

}
