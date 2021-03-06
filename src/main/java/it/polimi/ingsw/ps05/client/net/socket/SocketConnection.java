package it.polimi.ingsw.ps05.client.net.socket;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.net.ClientMessageVisitor;
import it.polimi.ingsw.ps05.client.net.Connection;
import it.polimi.ingsw.ps05.net.message.NetMessage;


import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alberto on 27/06/2017.
 */
public class SocketConnection implements Connection {

    public static final int port = 11717;

    private Socket socket;
    private Stream stream;
    private NetMessage message;


    //todo throwa o gestisce?
    public SocketConnection(String server, int port) throws IOException {
        socket = new Socket(server, port);
        this.stream = new Stream(this.socket);

    }


    @Override
    public void listen() {
    		while (true){
    			try {
    				System.out.println("ready to take in data");
					message = stream.takeInData();
					System.out.println("received something");
					if (message != null){
						synchronized (this) {
							System.out.println("received not null");
							System.out.println(message.toString());
							ClientMessageVisitor messageTaker =
									Client.getInstance().getMessageTaker();
							messageTaker.setInputMessage(message);
							flushInBuff();
							messageTaker.getSem().release(1);
							System.out.println("sem released");
						}
					}
					System.out.println("after condition");
				} catch (ClassNotFoundException | IOException e) {
					System.err.println("Connessione persa");
					System.exit(-1);
				}
    		}
    }

	@Override
	public void send(NetMessage mess) {
		try {
			System.out.println("sto per inviare sullo stream");
			stream.sendData(mess);
			System.out.println("Messaggio mandato");
		} catch (IOException e) {
			System.err.println("Messaggio non mandato");
			System.exit(-1);
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
