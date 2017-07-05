package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.TimerTask;

/**
 * Created by Alberto on 29/06/2017.
 */
public class InactivePlayerTask extends TimerTask {
	
	private Game game;
	private PlayerClient player;

    public InactivePlayerTask(Game game, PlayerClient player){
    	this.game = game;
        this.player = player;
    }
    
    

    @Override
    public void run() {
    	game.getState().nextState();
        player.setInactive();
    }
}
