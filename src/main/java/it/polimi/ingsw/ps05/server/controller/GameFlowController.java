package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;
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

	public ExcommunicationTriggerListener exTrigger;

	public GameFlowController(Game game){
		System.out.println("GFLWCTRL start");
        this.game = game;
        this.exTrigger = new ExcommunicationTriggerListener(this);
        this.game.gettManager().addObserver(exTrigger);
		System.out.println("GFLWCTRL cons end");
	}

	public synchronized void setGameInput(NetMessage gameInput) {
		this.gameInput = gameInput;
		notifyAll();
	}

	public void sendUpdateMsg(){
		System.out.println("Sending Update Msg");
		Board board = this.game.getBoard();
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
		System.out.println("Game end: " + game.end);
		while(!game.end){
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

			} catch (InterruptedException e ){
				e.printStackTrace();
			} catch (Exception f){
				f.printStackTrace();
			}
		}
		if (game.end){

		}
	}

	public int evaluateVictoryPts(Player pl){
		int tot = pl.getResource(VictoryResource.ID).getValue();
		tot = tot + game.getBoard().getBlueCardsConversion().get(pl.getBlueCardList().size()).getValue();
		tot = tot + game.getBoard().getGreenCardsConversion().get(pl.getGreenCardList().size()).getValue();
		tot = tot + game.getBoard().getFaithPath().get(pl.getResource(FaithResource.ID).getValue()).getValue();
		// TODO bisogna controllare chi ha più punti militari tra tutti
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
