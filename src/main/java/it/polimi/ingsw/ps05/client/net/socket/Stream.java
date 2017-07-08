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

	public Stream(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(this.socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(this.socket.getInputStream());
	}

	public NetMessage takeInData() throws ClassNotFoundException, IOException{
		NetMessage m = (NetMessage)in.readObject();
		return m;
	}

	public void sendData(NetMessage obj) throws IOException{
		out.reset();
		out.writeObject(obj);
		out.flush();
	}
}
