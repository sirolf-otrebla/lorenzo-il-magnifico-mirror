package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.EnteringLobbyMessage;
import it.polimi.ingsw.ps05.net.message.LobbyMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Semaphore;

/** Lobby class: where players wait for game's start.
 * 	this is a Runnable Class an thus will have a dedicated thread for each instance of it.
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

	/**Constructor for lobby object
	 *
	 * @param lobbyLifeTime			time before the game automatically start. there is a minimum threshold of
	 *                              players connected, which is 2 players at this moment.
	 * @param useCompleteRules		describe if the game will use or not the complete rules set.
	 * @param useCustomBonusTiles	describe if the game will use custom bonus tiles, loaded from file.
	 */
	public Lobby(int lobbyLifeTime, boolean useCompleteRules, boolean useCustomBonusTiles){
		connectedClientArrayList = new ArrayList<>();
		this.lobbyLifeTime = lobbyLifeTime;
		sem = new Semaphore(0);
		this.useCompleteRules = useCompleteRules;
		this.useCustomBonusTiles = useCustomBonusTiles;
	}

	/** this method adds a newly connected player to the lobby
	 *
	 * @param client	the client that has to be added to lobby
	 */
	public void addToLobby(PlayerClient client){
		System.out.println("adding player to lobby");
		if (connectedClientArrayList.size() == 1){
			this.timer = new Timer();
			this.timer.schedule(new StartGameTimerTask(this), lobbyLifeTime);
		}
		this.connectedClientArrayList.add(client);
		ArrayList<String> usernamesArrayList = new ArrayList<>();
		for (PlayerClient playerClient: this.connectedClientArrayList) {
			usernamesArrayList.add(playerClient.getUsername());
		}
		client.sendMessage(new EnteringLobbyMessage(true, usernamesArrayList));
		for (PlayerClient playerClient: this.connectedClientArrayList) {
			//if(!client.equals(playerClient))
				playerClient.sendMessage(new EnteringLobbyMessage(false, usernamesArrayList));
		}

		// fa partire il metodo run di sotto
		System.out.println("rilascio lock");
		sem.release();
		System.out.println("Sem queue length: " + sem.availablePermits());

	}

	/**
	 *  this method removes the last player connected from the client's list.
	 */
	public synchronized void removeHeadFromLobby(){
		if(this.connectedClientArrayList.size() > 0) connectedClientArrayList.remove(connectedClientArrayList.size() -1);
	}

	/**
	 * this method removes all player from the lobby client's list.
	 */
	public void removeAllPlayers(){
		removeHeadFromLobby();
		if (this.connectedClientArrayList.size() > 0) removeAllPlayers();
	}

	/**
	 * this method removes a specific client from lobby.
	 * @param client	this is the {@link PlayerClient} that is going to be removed from lobby
	 */
	public void removePlayerFromLobby(PlayerClient client){
		this.connectedClientArrayList.remove(client);
		if (connectedClientArrayList.size() <= 1 ) timer.cancel();
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> usernamesArrayList = new ArrayList<>();
		for (PlayerClient playerClient: this.connectedClientArrayList) 
			usernamesArrayList.add(playerClient.getUsername());
		for (PlayerClient playerClient: this.connectedClientArrayList) 
				playerClient.sendMessage(new EnteringLobbyMessage(false, usernamesArrayList));
	}

	/** this is the Lobby's runtime: here the lobby listens for newly connected {@link PlayerClient}
	 *	when the timer starts, or when the maximum number of players is reached, startGame is called.
	 */
	@Override
	public void run() {
		while(true) {
			System.out.println("Starting Lobby Thread");
			synchronized (this) {
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

	}

	/**
	 * this method starts the game, removing players from lobby and starting a new thread dedicated for each newly
	 * started game.
	 */
	public void startGame(){

		Thread t = new Thread(new GameThread(this.useCompleteRules, this.useCustomBonusTiles, connectedClientArrayList));
		t.start();
		removeAllPlayers();


	}
}
