package it.polimi.ingsw.ps05.client.net.socket;

import it.polimi.ingsw.ps05.server.net.socket.Stream;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alberto on 27/06/2017.
 */
public class socketConnection {

    public static final int port = 54322;

    private Socket socket;
    private Stream socketStream;


    //todo throwa o gestisce?
    public socketConnection(String server) throws IOException {
        socket = new Socket(server, socketConnection.port);
        this.socketStream = new Stream(this.socket);
    }
}
