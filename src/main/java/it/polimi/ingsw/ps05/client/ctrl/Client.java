package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 27/06/2017.
 */
public class Client {

    private static Client client;
    private ServerInterface serverInterface;
    private Client(){

    }

    public static Client getInstance(){
        if (client== null)
            client = new Client();
        return client;
    }

    public void waitUpdate(){
        //todo
    }

    public void sendToServer(NetMessage message){
        //todo
    }
}
