package it.polimi.ingsw.ps05.server.net.socket;

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
		return (NetMessage)in.readObject();
	}

	public void sendData(NetMessage obj) throws IOException{
		System.out.println("serializzo oggetto ed invio");
		if (obj instanceof GameUpdateMessage){
			System.out.println("game update message");
			GameStatus s = ((GameUpdateMessage) obj).getGameStatus();
			for (Player p : s.getPlayerHashMap().values()){
				System.out.println("SocketConn: " + p.getUsername());
				System.out.println("SocketConnBlu: " + p.getBlueCardList());
				System.out.println("SocketConnVerde: " + p.getGreenCardList());
				System.out.println("SocketConnGiallo: " + p.getYellowCardList());
				System.out.println("SocketConnViola: " + p.getVioletCardList());
				for (Resource r : p.getResourceList()){
					System.out.println(r.getID() + " " + r.getValue());
				}
				System.out.println(" ");
			}

		}
		System.out.println("object to send hash: \n" + obj.hashCode());
		out.reset();
		out.writeObject(obj);
		out.flush();
		count++;
		System.out.println("HO SCRITTO " + count + " VOLTE");
	}
}
