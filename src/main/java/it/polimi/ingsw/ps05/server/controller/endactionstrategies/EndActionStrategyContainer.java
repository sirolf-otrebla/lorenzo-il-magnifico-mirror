package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.server.controller.Game;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 06/07/2017.
 */
public class EndActionStrategyContainer {
    private Game game;
    private ArrayList<EndActionStrategy> chosenStrategies = new ArrayList<>();
    private EndActionStrategy defaultStrategy;
    private Semaphore semaphore = new Semaphore(0);

    public EndActionStrategyContainer(EndActionStrategy defaultStrategy, Game game) {
        this.defaultStrategy = defaultStrategy;
        this.game = game;
    }


    public void resetStrategy(){
        this.chosenStrategies = new ArrayList<>();
        this.chosenStrategies.add(defaultStrategy);
    }

    public void setChosenStrategy(EndActionStrategy strategy){
        this.chosenStrategies.add(strategy);
    }

    public void executeStrategy(){
        for ( EndActionStrategy strategy: this.chosenStrategies) {
            strategy.execute(this);
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void executeDefaultStrategy(){
        defaultStrategy.execute(this);
    }

    public void StrategyEnded(){
        semaphore.release();
    }

    public Game getGame() {
        return game;
    }


}
