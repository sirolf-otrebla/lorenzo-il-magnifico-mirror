package it.polimi.ingsw.ps05.server.controller;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class Server {

    private Lobby serverLobby;
    private static Server instance;
    private int gamesNumber;
    private ArrayList<Game> gameList;

    private Server(){

    }

    public static Server getInstance(){
        if (instance == null ) instance = new Server();
        return instance;
    }

    public void  startServer(int waitingTime, boolean useCompleteRules, boolean useCustomTiles){
        serverLobby = new Lobby(waitingTime, useCompleteRules, useCustomTiles);


    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void registerGame(Game game){
        gameList.add(game);
        gamesNumber++;

    }
}
