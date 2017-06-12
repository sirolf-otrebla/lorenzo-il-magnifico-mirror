package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;
import sun.reflect.annotation.ExceptionProxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class GameFlowController implements Runnable, Observer{
	
	Turn currentTurn;
	Player activePlayer;
	private NetMessage gameInput = null;
	private Game game;
	public GameFlowController(Game game){
		this.game = game;

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
				ArrayList<Player> PlayerOrder = thisTurn.getPlayerOrder();
				Iterator<Player> plOrdIt = PlayerOrder.iterator();
				while (plOrdIt.hasNext()) {
					this.activePlayer = plOrdIt.next();
					// definire un metodo clone nel giocatore per
					evaluatePermanentEffect();
					NetMessage inputMessage = this.getInput();
					inputMessage.acceptVisitor(this);
				}
			} catch (InterruptedException e ){

			} catch (Exception f){

			}
		}
		if (game.end){

		}
	}

	public void visit(ActionMessage mess) throws Exception{
		Player pl = mess.getPlayerBefore();
		validatePlayer(pl);
		this.activePlayer.doAction(mess.getAction().getFamiliar(),
				mess.getAction().getPosition());


	}
	public void visit(ExitGameMessage mess){
		// gestione permanenza partita
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
			if(activePlayer.getResource(res.getId()).getValue() != res.getValue())
				throw new Exception();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
