package it.polimi.ingsw.ps05.server.net;

import java.io.IOException;

import it.polimi.ingsw.ps05.server.net.socket.SocketSide;

public class ServerApp {
	//imposto di default una porta, ma all'avvio si potrà far selezionare
	int port = 63400;
	
	public ServerApp(int port) throws IOException{
		this.port = port;
		SocketSide s = new SocketSide(port);
		System.out.println("Server inizializzato");
		Thread t = new Thread(s);
		t.setDaemon(true);
		t.start();
	}
}
