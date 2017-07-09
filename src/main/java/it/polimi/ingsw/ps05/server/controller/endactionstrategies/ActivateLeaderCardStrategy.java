package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

/**
 * Created by Alberto on 09/07/2017.
 */
public class ActivateLeaderCardStrategy implements EndActionStrategy {
    @Override
    public void execute(EndActionStrategyContainer container) {
        container.removeDefaultFromStrategies();
        container.strategyEnded();
    }
}
