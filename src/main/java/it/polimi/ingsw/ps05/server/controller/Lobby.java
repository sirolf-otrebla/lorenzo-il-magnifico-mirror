package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Alberto on 02/07/2017.
 */
public class Lobby implements Runnable {

    private ArrayList<PlayerClient> connectedClientArrayList;
    private Timer timer;
    private int lobbyLifeTime;
    private boolean useCompleteRules = true;
    private boolean useCustomBonusTiles = false;
    public static final int MAX_PLAYER_NUM = 4;

    public Lobby(int lobbyLifeTime, boolean useCompleteRules, boolean useCustomBonusTiles){
        connectedClientArrayList = new ArrayList<>();
        this.timer = new Timer();
        this.lobbyLifeTime = lobbyLifeTime;
}
    public synchronized void addToLobby(PlayerClient client){
        if (connectedClientArrayList.size() == 1){
            this.connectedClientArrayList.add(client);
            this.timer.schedule(new StartGameTimerTask(this), lobbyLifeTime);
        }
    }

    public synchronized void removeHeadFromLobby(){
        if(this.connectedClientArrayList != null) connectedClientArrayList.remove(connectedClientArrayList.size() -1);
    }

    public void removeAllPlayers(){
        removeHeadFromLobby();
       if (this.connectedClientArrayList != null) removeAllPlayers();
    }

    public void removePlayerFromLobby(PlayerClient client){
        this.connectedClientArrayList.remove(client);
        if (connectedClientArrayList.size() <= 1 ) timer.cancel();
    }

    @Override
    public void run() {
        synchronized (this){
            while (this.connectedClientArrayList.size() < MAX_PLAYER_NUM) try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("something gone wrong :( ");
                //TODO gestire sta cazzo di eccezione
            }
            startGame();
        }

    }

    public void startGame(){
        Game game = new Game(this.useCompleteRules, this.useCustomBonusTiles, Server.getInstance().getGamesNumber() +1, connectedClientArrayList);
        removeAllPlayers();



    }
}
