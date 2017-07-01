package it.polimi.ingsw.ps05.server.controller;


import java.util.TimerTask;

/**
 * Created by Alberto on 02/07/2017.
 */
public class StartGameTimerTask extends TimerTask {

    private Lobby lobby;
    public StartGameTimerTask(Lobby lobby){
        this.lobby = lobby;
    }
    @Override
    public void run() {
        lobby.startGame();
    }
}
