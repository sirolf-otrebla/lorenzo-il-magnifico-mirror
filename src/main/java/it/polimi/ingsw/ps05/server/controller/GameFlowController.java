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

/**
 * this class implement the Game Flow controller, which is the main controller class. the
 * game flow controller deals with keeping the game proceeding, calling turns and rounds and
 * modifying classes such as {@link Game} or others.
 */
public class GameFlowController implements Runnable {


	private NetMessage gameInput = null;
	private Game game;

	public ExcommunicationTriggerListener exTrigger = new ExcommunicationTriggerListener(this);

	/**
	 * this is the main constructor for GameFlowController
	 * @param game the game Flow Controller needs a reference to the related {@link Game} in order to be
	 *             instantiated
	 */
	public GameFlowController(Game game){
		System.out.println("GFLWCTRL start");
        this.game = game;
        this.exTrigger = new ExcommunicationTriggerListener(this);
		System.out.println("GFLWCTRL cons end");
	}

	/**this method sets the game imput, which will be further handled by {@link GameCommandsVisitor}
	 * @param gameInput a {@link NetMessage} representing the game input
	 */
	public synchronized void setGameInput(NetMessage gameInput) {
		this.gameInput = gameInput;
		notifyAll();
	}

	/**
	 * this method is designed to send an update message to all player regarding the game status.
	 * is useful if the client, like in this implementation, takes care not about the single Actions but about
	 * the whole Game Status.
	 * @see GameUpdateMessage
	 */
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

	/**
	 * the game flow controller is a runnable Class an this is it's main loop, representing the main operation
	 * that are needed to keep the game flow.
	 */
	@Override
	public void run()  {
		System.out.println("RUN START");
		System.out.println("Game end: " + game.end);
		this.game.gettManager().addObserver(exTrigger);
		while(!game.end){
			try {
				System.out.println("game flow ctrl pre turn  ");
				Turn thisTurn = this.game.gettManager().getTurn();
				PlayerClient plClient =
                        game.getPlayerInGame().get(thisTurn.getPlayerOrder().get(0).getPlayerID());
				System.out.println("PlClient : " + plClient);
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

	/** this method takes care of evaluating victory points and conditions. it is not communicating with the
	 * client, so other code takes the responsibility of dealing with Player Clients.
	 * @param pl represent the player that the method has to handle
	 * @return the total victory points related to the player {@code pl}
	 */
	public int evaluateVictoryPts(Player pl){
		int tot = pl.getResource(VictoryResource.ID).getValue();
		tot = tot + game.getBoard().getBlueCardsConversion().get(pl.getBlueCardList().size()).getValue();
		tot = tot + game.getBoard().getGreenCardsConversion().get(pl.getGreenCardList().size()).getValue();
		tot = tot + game.getBoard().getFaithPath().get(pl.getResource(FaithResource.id).getValue()).getValue();
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
