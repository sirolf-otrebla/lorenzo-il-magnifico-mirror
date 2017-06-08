package it.polimi.ingsw.ps05.net.Server.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import it.polimi.ingsw.ps05.net.Server.ServerPlayer;

public class SocketSide implements Runnable {
	
	private ServerSocket server;
	private int id= 0;
	private ArrayList<ServerPlayer> connected = new ArrayList<ServerPlayer>();
	
	public SocketSide(int port){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		//dentro al ciclo si accettano connessioni
		while (true){
			try {
				ServerPlayer p = new ServerPlayer(server.accept(), id++);
				connected.add(p);
				Thread t = new Thread(p);
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
