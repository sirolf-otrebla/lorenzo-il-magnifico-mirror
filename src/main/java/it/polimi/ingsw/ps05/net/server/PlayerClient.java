
package it.polimi.ingsw.ps05.net.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.RejectedMessage;

public class PlayerClient extends Observable implements Runnable, Observer{
	private int id;
	private boolean isActive = false;
	private boolean inGame = false;
	private LimConnection connection;

	private Player pl = null;
	private boolean plExists = false;
	private String username;
	private boolean active;

	public PlayerClient(LimConnection netHandler, int id){
	    this.connection = netHandler;
	    this.id = id;
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

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getId(){
		return id;
	}
    

}

