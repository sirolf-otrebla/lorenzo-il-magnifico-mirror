package it.polimi.ingsw.ps05.net.server.socket;


import java.io.IOException;
import java.net.Socket;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.LimConnection;

/**
 * Created by Alberto on 08/06/2017.
 */
public class SocketConn extends LimConnection {
	
	private Socket socket;
	private Stream stream;
	private NetMessage message;
	
	public SocketConn(Socket socket){
		this.socket = socket;
		try {
			stream = new Stream(this.socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Override
    public void listen() {
    		while (true){
    			try {
					message = stream.takeInData();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    }

	@Override
	public void send(NetMessage mess) {
		// TODO Auto-generated method stub
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
