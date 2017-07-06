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
	private boolean useCompleteRules = false;
	private boolean useCustomBonusTiles = false;
	public static final int MAX_PLAYER_NUM = 4;
	private Semaphore sem;

	public Lobby(int lobbyLifeTime, boolean useCompleteRules, boolean useCustomBonusTiles){
		connectedClientArrayList = new ArrayList<>();
		this.timer = new Timer();
		this.lobbyLifeTime = lobbyLifeTime;
		sem = new Semaphore(0);
		this.useCompleteRules = useCompleteRules;
		this.useCustomBonusTiles = useCustomBonusTiles;
	}
	public void addToLobby(PlayerClient client){
		System.out.println("adding player to lobby");
		if (connectedClientArrayList.size() == 1){
			this.timer.schedule(new StartGameTimerTask(this), lobbyLifeTime);
		}
		this.connectedClientArrayList.add(client);
		ArrayList<String> usernamesArrayList = new ArrayList<>();
		for (PlayerClient playerClient: this.connectedClientArrayList) {
			usernamesArrayList.add(playerClient.getUsername());
		}
		client.sendMessage(new EnteringLobbyMessage(true, usernamesArrayList));
		for (PlayerClient playerClient: this.connectedClientArrayList) {
			if(!client.equals(playerClient))
				playerClient.sendMessage(new EnteringLobbyMessage(false, usernamesArrayList));
		}

		// fa partire il metodo run di sotto
		System.out.println("rilascio lock");
		sem.release();
		System.out.println("Sem queue length: " + sem.availablePermits());
	}

	public synchronized void removeHeadFromLobby(){
		if(this.connectedClientArrayList.size() > 0) connectedClientArrayList.remove(connectedClientArrayList.size() -1);
	}

	public void removeAllPlayers(){
		removeHeadFromLobby();
		if (this.connectedClientArrayList.size() > 0) removeAllPlayers();
	}

	public void removePlayerFromLobby(PlayerClient client){
		this.connectedClientArrayList.remove(client);
		if (connectedClientArrayList.size() <= 1 ) timer.cancel();
	}

	@Override
	public void run() {
		System.out.println("Starting Lobby Thread");
		synchronized (this){
			try {
				sem.acquire(MAX_PLAYER_NUM);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("something gone wrong :( ");
				//TODO gestire sta cazzo di eccezione
			}
			System.out.println("Starting game!!!");
		}
		timer.cancel();
		startGame();

	}

	public void startGame(){
		Game game = new Game(this.useCompleteRules, this.useCustomBonusTiles,
				Server.getInstance().getGamesNumber() +1, connectedClientArrayList);
		removeAllPlayers();
		try {
			game.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
}
