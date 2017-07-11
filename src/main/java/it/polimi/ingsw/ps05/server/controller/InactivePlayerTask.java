package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.gamemessages.PassActionMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.TimerTask;

/**
 * this class represent the task executed when a certain client does not respond in time when it has
 * to make an action. it extends {@code TimerTask}
 * @see TimerTask
 * @see java.util.Timer
 * @see PlayerClient
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
        System.out.println("fine azione, ci hai messo troppo tempo baby");
        game.getState().setInputMessage(new PassActionMessage());
    }
}
