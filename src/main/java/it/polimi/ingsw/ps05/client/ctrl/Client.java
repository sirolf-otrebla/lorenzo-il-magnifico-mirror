package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.NetMessage;

import java.io.Serializable;

/**
 * Created by Alberto on 27/06/2017.
 */
public class Client {

    private static Client client;
    private ServerInterface serverInterface;
    private GameStatus gameStatus;
    private Client(){
        //TODO ATTIVA LOBBY
        // TODO GESTISCE CONNESSIONE
    }

    private void createConnection(String Url){
        this.serverInterface = ServerInterface.getInstance();
    }

    public static Client getInstance(){
        if (client== null)
            client = new Client();
        return client;
    }

    public void sendToServer(NetMessage message){

    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
