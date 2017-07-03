package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.EnteringLobbyMessage;
import it.polimi.ingsw.ps05.net.message.LobbyMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Semaphore;

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
    private Semaphore sem;

    public Lobby(int lobbyLifeTime, boolean useCompleteRules, boolean useCustomBonusTiles){
        connectedClientArrayList = new ArrayList<>();
        this.timer = new Timer();
        this.lobbyLifeTime = lobbyLifeTime;
        sem = new Semaphore(0);
}
    public synchronized void addToLobby(PlayerClient client){
        System.out.println("adding player to lobby");
        this.connectedClientArrayList.add(client);
        if (connectedClientArrayList.size() == 1){
            this.timer.schedule(new StartGameTimerTask(this), lobbyLifeTime);
        }
        ArrayList<String> usernamesArrayList = new ArrayList<>();
        for (PlayerClient playerClient: this.connectedClientArrayList) {
            usernamesArrayList.add(playerClient.getUsername());
        }
        client.sendMessage(new EnteringLobbyMessage(true, usernamesArrayList));
        for (PlayerClient playerClient: this.connectedClientArrayList) {
            if(!client.equals(playerClient))
                playerClient.sendMessage(new EnteringLobbyMessage(false, usernamesArrayList));
        }
        sem.release();
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
            try {
                sem.acquire(MAX_PLAYER_NUM);
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
