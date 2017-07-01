package it.polimi.ingsw.ps05.server.controller;

/**
 * Created by Alberto on 02/07/2017.
 */
public class Server {

    private Lobby serverLobby;
    private static Server instance;
    private int GamesNumber;

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
        return GamesNumber;
    }
}
