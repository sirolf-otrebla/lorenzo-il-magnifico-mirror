package it.polimi.ingsw.ps05.net.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import it.polimi.ingsw.ps05.net.server.PlayerClient;

public class SocketSide implements Runnable {
	
	private ServerSocket server;
	private int id= 0;
	private ArrayList<PlayerClient> connected = new ArrayList<PlayerClient>();
	
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
			// try {
				// TODO: CORREGGERE
                // PlayerClient p = new PlayerClient(server.accept(), id++);
				// connected.add(p);
				// Thread t = new Thread(p);
				// t.start();
			//}   catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			// }
		}
	}

}
