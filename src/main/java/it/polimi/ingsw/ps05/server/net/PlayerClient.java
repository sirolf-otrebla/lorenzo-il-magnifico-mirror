
package it.polimi.ingsw.ps05.server.net;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.RejectedMessage;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.InactivePlayerTask;
import it.polimi.ingsw.ps05.server.controller.MessageObserver;
import it.polimi.ingsw.ps05.server.controller.Server;

public class PlayerClient extends Observable implements Runnable, Observer{
	private int id;
	private boolean isActive = false;
	private boolean inGame = false;
	private LimConnection connection;
	private Timer timer;
	private Observer messageObserver;
	private Game game = null;
	private boolean logged = false;

	private static final int  ACT_WAITING_TIME = 120000;

	private Player pl = null;
	private boolean plExists = false;
	private String username;
	private boolean active;

	public PlayerClient(LimConnection netHandler, int id){
	    this.connection = netHandler;
	    this.id = id;
	    this.messageObserver = MessageObserver.getInstance();
		this.timer = new Timer();
    }

	@Override
	public void run() {
		if (connection != null)
		    connection.listen();
	}

    @Override
    public synchronized void update(Observable o, Object message) {
        // da completare quando arriva un messaggio

		if(isActive){
			NetMessage mess = this.connection.getInputMessage();
			this.connection.flushInBuf();
			setChanged();
			notifyObservers(mess);

		}else{
			RejectedMessage mess = new RejectedMessage();
			this.connection.send(mess);
		}
    }

    public void BuildPlayer( ColorEnumeration color){
		if(this.pl == null){
			this.pl = new Player(id, username, color );
			plExists = true;
		}
	}

	public Player getPlayer(){
    	return this.pl;
	}
	
	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(Game game) {
		this.inGame = true;
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive() {
		this.active = true;
		InactivePlayerTask task = new InactivePlayerTask(this.game, this);
		timer.schedule(task, PlayerClient.ACT_WAITING_TIME);
	}

	public void setInactive(){
		this.timer.cancel();
		this.active = false;
	}
	
	public int getId(){
		return id;
	}
    

	public void sendMessage(NetMessage message){
		connection.send(message);
	}


	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
}

