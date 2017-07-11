package it.polimi.ingsw.ps05.server.controller.startactionstrategies;

import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.EndActionStrategy;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 11/07/2017.
 */
public class StartActionStrategyContainer {
    private Game game;
    private ArrayList<StartActionStrategy> chosenStrategies = new ArrayList<>();
    private StartActionStrategy defaultStrategy;
    private Semaphore semaphore = new Semaphore(0);

    public StartActionStrategyContainer(StartActionStrategy defaultStrategy, Game game) {
        this.defaultStrategy = defaultStrategy;
        this.game = game;
    }


    public void resetStrategy(){
        this.chosenStrategies = new ArrayList<>();
        this.chosenStrategies.add(defaultStrategy);
    }

    public void setChosenStrategy(StartActionStrategy strategy){
        this.chosenStrategies.remove(defaultStrategy);
        this.chosenStrategies.add(strategy);
        this.chosenStrategies.add(defaultStrategy);
    }

    public void executeStrategy(){
        for ( StartActionStrategy strategy: this.chosenStrategies) {
            strategy.execute(this);
            try {
                semaphore.acquire();
                System.out.println("Strategy Semaphore released");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void executeDefaultStrategy(){
        defaultStrategy.execute(this);
    }

    public void strategyEnded(){
        semaphore.release();
    }

    public Game getGame() {
        return game;
    }

    public void removeDefaultFromStrategies(){
        this.chosenStrategies.remove(defaultStrategy);
    }


}
