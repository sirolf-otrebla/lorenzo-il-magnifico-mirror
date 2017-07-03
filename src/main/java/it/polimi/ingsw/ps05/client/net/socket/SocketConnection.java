package it.polimi.ingsw.ps05.client.net.socket;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.net.ClientMessageTaker;
import it.polimi.ingsw.ps05.client.net.Connection;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.socket.Stream;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

/**
 * Created by Alberto on 27/06/2017.
 */
public class SocketConnection implements Connection {

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
					if (message != null){
						synchronized (this) {
							ClientMessageTaker messageTaker =
									Client.getInstance().getMessageTaker();
							messageTaker.setInputMessage(message);
							messageTaker.getSem().release(1);
						}
					}
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
