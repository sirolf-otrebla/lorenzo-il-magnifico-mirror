package it.polimi.ingsw.ps05.net.server.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	public Object takeInData() throws ClassNotFoundException, IOException{
		return in.readObject();
	}

	public void sendData(Object obj) throws IOException{
		out.writeObject(obj);
	}
}
