package it.polimi.ingsw.ps05.server.net;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Lobby implements Observer {
	private static Lobby instance = null;
	private ArrayList<PlayerClient> playerInLobby= new ArrayList<PlayerClient>();
	private ArrayList<Game> gameList= new ArrayList<Game>();
	private int gameId = 0;
	
	private Lobby(){
		
	}
	
	public static Lobby getInstance(){
		if (instance == null) {
			instance = new Lobby();
		}
		return instance;
	}
	
	public void addPlayerToLobby(PlayerClient player){
		playerInLobby.add(player);
		player.addObserver(this);
	}
	
	private void removePlayerFromLobby(PlayerClient player){
		playerInLobby.remove(player);
		player.deleteObserver(this);
	}
	
	public void createGame(boolean useCompleteRules, boolean useCustomBonusTiles, PlayerClient player){
		Game game = new Game(useCompleteRules,useCustomBonusTiles, gameId++);
		game.addPlayer(player);
		gameList.add(game);
		removePlayerFromLobby(player);
	}
	
	public void deleteGame(Game game){
		gameList.remove(game);
		//TODO: playerInLobby.addAll(game.getPlayerInGame());
	}
	
	public void removePlayerFromGame(PlayerClient player, Game game){
		game.removePlayer(player);
		addPlayerToLobby(player);
	}

	@Override
	public void update(Observable o, Object arg) {
		//TODO
	}
}
