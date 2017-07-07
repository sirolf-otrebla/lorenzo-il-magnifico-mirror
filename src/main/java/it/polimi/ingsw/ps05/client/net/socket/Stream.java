package it.polimi.ingsw.ps05.client.net.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameUpdateMessage;

public class Stream {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private static int count = 0;

	public Stream(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(this.socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(this.socket.getInputStream());
	}

	public NetMessage takeInData() throws ClassNotFoundException, IOException{
		NetMessage m = (NetMessage)in.readObject();
		count++;
		System.out.println("HO RICEVUTO " + count + " VOLTE");
		System.out.println("Object read hash: \t" + m.hashCode());
		System.out.println("takeindata class: " + m.getClass());
		if (m instanceof GameUpdateMessage){
			System.out.println("game update message primo metodo!!!!!!!!!!!");
			GameStatus s = ((GameUpdateMessage) m).getGameStatus();
			for (Player p : s.getPlayerHashMap().values()){
				System.out.println("SocketConnPl: " + p.getUsername());
				System.out.println("SocketConnBlu: " + p.getBlueCardList());
				System.out.println("SocketConnVerde: " + p.getGreenCardList());
				System.out.println("SocketConnGiallo: " + p.getYellowCardList());
				System.out.println("SocketConnViola: " + p.getVioletCardList());
				for (Resource r : p.getResourceList()){
					System.out.println(r.getID() + " " + r.getValue());
				}
			}
		}
		return m;
	}

	public void sendData(NetMessage obj) throws IOException{
		out.reset();
		out.writeObject(obj);
		out.flush();
	}
}
