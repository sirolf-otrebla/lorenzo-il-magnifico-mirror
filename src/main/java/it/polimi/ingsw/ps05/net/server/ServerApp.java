package it.polimi.ingsw.ps05.net.server;

import it.polimi.ingsw.ps05.net.server.socket.SocketSide;

public class ServerApp {
	static //imposto di default una porta, ma all'avvio si potrà far selezionare
	int port = 63400;
	public static void main(String args[]){
		SocketSide s = new SocketSide(port);
		Thread t = new Thread(s);
		t.setDaemon(true);
		t.start();
	}
}
