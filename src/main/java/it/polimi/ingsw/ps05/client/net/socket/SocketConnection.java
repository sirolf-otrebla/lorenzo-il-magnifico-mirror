package it.polimi.ingsw.ps05.client.net.socket;

import it.polimi.ingsw.ps05.client.net.Connection;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.socket.Stream;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alberto on 27/06/2017.
 */
public class SocketConnection extends Connection{

    public static final int port = 54322;

    private Socket socket;
    private Stream stream;
    private NetMessage message;


    //todo throwa o gestisce?
    public SocketConnection(String server) throws IOException {
        socket = new Socket(server, SocketConnection.port);
        this.stream = new Stream(this.socket);
    }


    @Override
    public void listen() {
    		while (true){
    			try {
					message = stream.takeInData();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
    		}
    }

	@Override
	public void send(NetMessage mess) {
		try {
			stream.sendData(mess);
		} catch (IOException e) {
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
