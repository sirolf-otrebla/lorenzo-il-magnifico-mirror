package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ServantResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.StoneResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.VictoryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.WoodResource;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameUpdateMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

public class GameFlowController implements Runnable {


	private NetMessage gameInput = null;
	private Game game;

	private static final int TURN_PER_GAME = 6;
	private int turnDone = 0;

	public ExcommunicationTriggerListener exTrigger = new ExcommunicationTriggerListener(this);

	public GameFlowController(Game game){
		System.out.println("GFLWCTRL start");
		this.game = game;
		this.exTrigger = new ExcommunicationTriggerListener(this);
		System.out.println("GFLWCTRL cons end");
	}

	public synchronized void setGameInput(NetMessage gameInput) {
		this.gameInput = gameInput;
		notifyAll();
	}

	public void sendUpdateMsg(){
		System.out.println("Sending Update Msg");
		Board board = this.game.getBoard();
		board.setTurnNumber(this.game.gettManager().getTurn().getTurnNumber());
		Integer activePlayerId = this.game.getActivePlayer().getPlayerID();
		ArrayList<Player> playerStatusList = new ArrayList<Player>();
		for (PlayerClient p : this.game.getPlayerInGame().values())
			playerStatusList.add(p.getPlayer());
		for (PlayerClient p : this.game.getPlayerInGame().values()){
			GameStatus status = new GameStatus(playerStatusList, board, p.getPlayer(), activePlayerId);
			GameUpdateMessage gameUpdateMessage = new GameUpdateMessage(status);
			p.sendMessage(gameUpdateMessage);
		}

	}

	@Override
	public void run()  {
		System.out.println("RUN START");
		this.game.gettManager().addObserver(exTrigger);
		while(turnDone < TURN_PER_GAME){
			try {
				System.out.println("game flow ctrl pre turn  ");
				Turn thisTurn = this.game.gettManager().getTurn();
				PlayerClient plClient =
						game.getPlayerInGame().get(thisTurn.getPlayerOrder().get(0).getPlayerID());
				System.out.println("PlClient : " + plClient);
				plClient.setActive();
				RoundController turnRoundCtrl = new RoundController(thisTurn, game);
				turnRoundCtrl.executeTurn();
				this.game.gettManager().loadNextTurn();
				turnDone++;
			} catch (InterruptedException e ){
				e.printStackTrace();
			} catch (Exception f){
				f.printStackTrace();
			}
		}
		for (int i = 0; i < game.getPlayerInGame().size(); i++){
			((Player)game.getPlayerInGame().values().toArray()[i]).getResource(VictoryResource.ID).setValue(evaluateVictoryPts((Player)game.getPlayerInGame().values().toArray()[i]));
		}
		Player[] pList = new Player[game.getPlayerInGame().size()];
		for (int i = 0; i < game.getPlayerInGame().size(); i++){
			pList[i] = (Player)game.getPlayerInGame().values().toArray()[i];
		}

		for (int j = 0; j < pList.length - 1; j++) {
			for (int i = 0; i < pList.length - 1; i++) {
				if (pList[i].getResource(VictoryResource.ID).getValue().intValue() > pList[i + 1].getResource(VictoryResource.ID).getValue().intValue()) {
					Player temp = pList[i];
					pList[i] = pList[i + 1];
					pList[i + 1] = temp;
				}
			}
		}
		
		for (PlayerClient p : this.game.getPlayerInGame().values()){
			EndGameMessage message = new EndGameMessage(pList);
			p.sendMessage(message);
		}


	}

	public int evaluateVictoryPts(Player pl){
		int tot = pl.getResource(VictoryResource.ID).getValue();
		tot = tot + game.getBoard().getBlueCardsConversion().get(pl.getBlueCardList().size()).getValue();
		tot = tot + game.getBoard().getGreenCardsConversion().get(pl.getGreenCardList().size()).getValue();
		tot = tot + game.getBoard().getFaithPath().get(pl.getResource(FaithResource.id).getValue()).getValue();
		Player[] pList = new Player[game.getPlayerInGame().size()];
		for (int i = 0; i < game.getPlayerInGame().size(); i++){
			pList[i] = (Player)game.getPlayerInGame().values().toArray()[i];
		}

		for (int j = 0; j < pList.length - 1; j++) {
			for (int i = 0; i < pList.length - 1; i++) {
				if (pList[i].getResource(MilitaryResource.id).getValue().intValue() > pList[i + 1].getResource(MilitaryResource.id).getValue().intValue()) {
					Player temp = pList[i];
					pList[i] = pList[i + 1];
					pList[i + 1] = temp;
				}
			}
		}

		if (pl.equals(pList[0]) || (pl.equals(pList[1]) &&
				pList[0].getResource(MilitaryResource.id).getValue().intValue() ==
				pl.getResource(MilitaryResource.id).getValue().intValue())){
			tot = tot + 5;
		} else if (pl.equals(pList[1])){
			if (pList[2] != null && pList[2].getResource(MilitaryResource.id).getValue().intValue() !=
					pl.getResource(MilitaryResource.id).getValue().intValue())
				tot = tot + 2;
		}
		int ris = pl.getResource(GoldResource.id).getValue();
		ris = ris + pl.getResource(ServantResource.id).getValue();
		ris = ris + pl.getResource(StoneResource.id).getValue();
		ris = ris + pl.getResource(WoodResource.id).getValue();
		tot = (int) (tot + Math.floor(ris/5));
		return tot;
	}



	public Game getGame() {
		return game;
	}
}
