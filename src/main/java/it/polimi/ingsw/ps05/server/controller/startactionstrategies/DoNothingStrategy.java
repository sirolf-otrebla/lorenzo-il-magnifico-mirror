package it.polimi.ingsw.ps05.server.controller.startactionstrategies;

import it.polimi.ingsw.ps05.server.controller.Game;

/**
 * Created by Alberto on 11/07/2017.
 */
public class DoNothingStrategy implements StartActionStrategy {
	private Game game;
	private boolean toJump = false;
	public DoNothingStrategy(Game game, boolean toJump){
		this.game = game;
	}
	
	@Override
	public void execute(StartActionStrategyContainer container) {
		if (game.getGameFlowctrl().getRoundCtrl().getEndTurnReceived() < 4 && toJump){
			System.out.println("Ho eseguito la strategia");
			game.getState().nextState();
		}
		container.strategyEnded();
	}
	
}
