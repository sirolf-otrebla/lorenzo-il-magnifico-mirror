package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.TimerTask;

/**
 * Created by Alberto on 29/06/2017.
 */
public class InactivePlayerTask extends TimerTask {

    public InactivePlayerTask(Game game, PlayerClient player){
        game.getState().nextState();
        player.setInactive();
    }

    @Override
    public void run() {

    }
}
