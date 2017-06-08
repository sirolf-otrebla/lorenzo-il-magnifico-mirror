package it.polimi.ingsw.ps05.net.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

import it.polimi.ingsw.ps05.net.Server.Socket.Stream;

public class ServerPlayer extends Observable implements Runnable {
	private int id;
	private Socket client;
	private Stream stream;
	//fare costruttore con RMI e impostare parte RMI
	
	public ServerPlayer(Socket client, int id){
		this.id = id;
		this.client = client;
		try {
			stream = new Stream(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		//socket
		if (client != null){
			while(true){
				try {
					Object obj = stream.takeInData();
					//a chi interessa prendere gli input?
					//setChanged();
					//notifyObservers(obj);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else { //RMI
			
		}
		
	}

}
