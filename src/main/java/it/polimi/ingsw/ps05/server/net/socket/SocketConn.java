package it.polimi.ingsw.ps05.server.net.socket;


import java.io.IOException;
import java.net.Socket;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.LimConnection;

/**
 * Created by Alberto on 08/06/2017.
 */
public class SocketConn extends LimConnection {
	
	private Socket socket;
	private Stream stream;
	private NetMessage message;
	
	public SocketConn(Socket socket){
		System.out.println("viva gesù");
		this.socket = socket;
		try {
			stream = new Stream(this.socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Override
    public void listen() throws ClassNotFoundException, IOException {
    		while (true){
					message = stream.takeInData();
					System.out.println("ho ricevuto qualcosa");
					setChanged();
					notifyObservers();
    		}
    }

	@Override
	public void send(NetMessage mess) {
		try {
			stream.sendData(mess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void flushInBuff() {
		message = null;
	}

	@Override
	public NetMessage getInputMessage() {
		return message;
	}

	@Override
	public void flushInBuf() {

	}
}
