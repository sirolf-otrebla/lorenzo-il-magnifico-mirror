package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.server.controller.Game;

/**
 * Created by Alberto on 06/07/2017.
 */
public class EndActionStrategyContainer {
    private Game game;
    private EndActionStrategy chosenStrategy;
    private EndActionStrategy defaultStrategy;


    public EndActionStrategyContainer(EndActionStrategy defaultStrategy, Game game) {
        this.defaultStrategy = defaultStrategy;
        this.game = game;
    }


    public void resetStrategy(){
        this.chosenStrategy = defaultStrategy;
    }

    public void setChosenStrategy(EndActionStrategy strategy){
        this.chosenStrategy = strategy;
    }

    public void executeStrategy(){
        chosenStrategy.execute(this);
    }

    public Game getGame() {
        return game;
    }
}
