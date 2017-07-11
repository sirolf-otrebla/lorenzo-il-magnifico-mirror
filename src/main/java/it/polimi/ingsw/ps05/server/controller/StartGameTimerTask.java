package it.polimi.ingsw.ps05.server.controller;


import java.util.TimerTask;

/**
 * this task is responsible of starting a game when too much time has passed since a Client tries to connect
 * with the lobby.
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
