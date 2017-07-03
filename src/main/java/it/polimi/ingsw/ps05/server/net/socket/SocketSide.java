package it.polimi.ingsw.ps05.server.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


import it.polimi.ingsw.ps05.server.net.Lobby;

import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class SocketSide implements Runnable {
	
	private ServerSocket server;
	private int id= 0;
	private ArrayList<PlayerClient> connected = new ArrayList<PlayerClient>();
	
	public SocketSide(int port) throws IOException{
			server = new ServerSocket(port);
	}

	@Override
	public void run() {
		//dentro al ciclo si accettano connessioni
		System.out.println("Listening");
		while (true){
			try {

				//TODO RIMUOVERE I PRINTLN
				System.out.println("Ciao");
				SocketConn c = new SocketConn(server.accept());
				System.out.println("Nuova connessione");
				PlayerClient p = new PlayerClient(c, id++);
				connected.add(p);
				/*Lobby.getInstance().addPlayerToLobby(p);*/
				Thread t = new Thread(p);
				t.start();
			} catch (IOException e) {
	// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
