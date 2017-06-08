package it.polimi.ingsw.ps05.net.server;

import java.util.ArrayList;

public class Lobby {
	private static Lobby instance = null;
	private ArrayList<ServerPlayer> playerInLobby= new ArrayList<ServerPlayer>();
	private ArrayList<Game> gameList= new ArrayList<Game>();
	
	private Lobby(){
		
	}
	
	public static Lobby getInstance(){
		if (instance == null) {
			instance = new Lobby();
		}
		return instance;
	}
}
