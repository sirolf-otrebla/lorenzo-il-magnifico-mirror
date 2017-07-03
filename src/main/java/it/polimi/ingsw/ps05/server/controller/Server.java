package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.server.database.Database;
import it.polimi.ingsw.ps05.server.net.NetworkListener;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 02/07/2017.
 */
public class Server {

    private Lobby serverLobby;
    private static Server instance;
    private int gamesNumber;
    private ArrayList<Game> gameList;
    private NetworkListener listener;
    private HashMap<Integer, PlayerClient> globalClientMap;
    private Database userDatabase;

    public static final int DEFAULT_PORT = 11717;

    private Server(){

    }

    public static Server getInstance(){
        if (instance == null ) instance = new Server();
        return instance;
    }

    public void  startServer(int waitingTime, boolean useCompleteRules, boolean useCustomTiles){
        serverLobby = new Lobby(waitingTime, useCompleteRules, useCustomTiles);
        globalClientMap = new HashMap<>();
        userDatabase =  Database.getInstance();
        try {
            listener = new NetworkListener(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void registerGame(Game game){
        gameList.add(game);
        gamesNumber++;

    }

    public void putNewClient(PlayerClient client){
        this.globalClientMap.put(client.getId(), client);
    }

    public Database getUserDatabase() {
        return userDatabase;
    }

    public Lobby getServerLobby() {
        return serverLobby;
    }
}
