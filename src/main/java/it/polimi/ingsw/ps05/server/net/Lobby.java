package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.server.controller.Game;

import java.util.*;

public class Lobby implements Observer {
	private static Lobby instance = null;
	private ArrayList<PlayerClient> playerInLobby= new ArrayList<PlayerClient>();
	private HashMap<Integer, Game> gameList= new HashMap<>();

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
		ArrayList<PlayerClient> pl = new ArrayList<>();
		pl.add(player);
		Game game = new Game(useCompleteRules,useCustomBonusTiles, gameId++, pl);
		gameList.put(gameId, game);
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

	public HashMap<Integer, Game> getGameMap() {
		return this.gameList;
	}

	private void EnterGame(Integer GameId, PlayerClient playerClient){
		this.gameList.get(GameId).addPlayer(playerClient);
		this.removePlayerFromLobby(playerClient);
	}
}
