package it.polimi.ingsw.ps05.net.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps05.net.server.Socket.Stream;

public class ServerPlayer extends Observable implements Runnable, Observer{
	private int id;
	private Stream stream;

	private LimConnection connection;

	//fare costruttore con RMI e impostare parte RMI
	
	/* public ServerPlayer(Socket client, int id){
		this.id = id;
		this.client = client;
		try {
			stream = new Stream(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} */

	public ServerPlayer(LimConnection netHandler, int id){
	    this.connection = netHandler;
	    this.id = id;
    }

	@Override
	public void run() {
		if (connection != null)
		    connection.listen();
	}

    @Override
    public void update(Observable o, Object message) {
        // da completare quando arriva un messaggio
    }
}
