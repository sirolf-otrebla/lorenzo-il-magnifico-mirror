package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.server.controller.Game;

/**
 * Created by Alberto on 07/07/2017.
 */
public class MoveNextPlayerStrategy implements EndActionStrategy {

    private Game game;
    public MoveNextPlayerStrategy(Game game){
        this.game = game;
    }

    @Override
    public void execute(EndActionStrategyContainer container) {
        game.getState().nextState();

        //TODO GAME STATUS
    }
}
