package it.polimi.ingsw.ps05.net.server;

import java.util.ArrayList;

public class Lobby {
	private static Lobby instance = null;
	private ArrayList<ServerPlayer> playerInLobby= new ArrayList<ServerPlayer>();
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
	
	public void addPlayerToLobby(ServerPlayer player){
		playerInLobby.add(player);
	}
	
	private void removePlayerFromLobby(ServerPlayer player){
		playerInLobby.remove(player);
	}
	
	public void createGame(boolean useCompleteRules, boolean useCustomBonusTiles, ServerPlayer player){
		Game game = new Game(useCompleteRules,useCustomBonusTiles, gameId++);
		game.addPlayer(player);
		gameList.add(game);
		removePlayerFromLobby(player);
	}
	
	public void deleteGame(Game game){
		gameList.remove(game);
		playerInLobby.addAll(game.getPlayerInGame());
	}
	
	public void removePlayerFromGame(ServerPlayer player, Game game){
		game.removePlayer(player);
		addPlayerToLobby(player);
	}
}
