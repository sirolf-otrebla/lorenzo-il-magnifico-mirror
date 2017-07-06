package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.net.message.GameUpdateMessage;
import it.polimi.ingsw.ps05.net.message.RejoinMessage;
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
    private ArrayList<Game> gameList = new ArrayList<>();
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
        globalClientMap = new HashMap<>();
        this.serverLobby = new Lobby(waitingTime,useCompleteRules, useCustomTiles);
        Thread lobbyThread = new Thread(this.serverLobby);
        lobbyThread.start();
        userDatabase =  Database.getInstance();
        userDatabase.deleteDatabase();
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
    	boolean addedToGame = false;
    	for (Game g : gameList){
    		if (g.getPlayerInGame().get(client.getId()) != null){
    			client.setPlayer(g.getPlayerInGame().get(client.getId()).getPlayer());
    			g.getPlayerInGame().put(client.getId(), client);
    			ArrayList<Player> p = new ArrayList<>();
    			for (PlayerClient pl : g.getPlayerInGame().values()){
    				p.add(pl.getPlayer());
    			}
    			GameStatus s = new GameStatus(p, g.getBoard(), client.getPlayer(), g.getActivePlayer().getPlayerID());
    			client.sendMessage(new RejoinMessage(new GameSetupMessage(s), new GameUpdateMessage(s)));
    			
    			addedToGame = true;
    			break;
    		}
    	}
    	if (!addedToGame){
    		this.globalClientMap.put(client.getId(), client);
            this.serverLobby.addToLobby(client);
    	}
    }

    public Database getUserDatabase() {
        return userDatabase;
    }

    public Lobby getServerLobby() {
        return serverLobby;
    }
}
