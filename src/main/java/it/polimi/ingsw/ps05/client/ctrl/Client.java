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
    private String username;
    private Integer id;
    private Client(){
        //TODO ATTIVA LOBBY
        //TODO GESTISCE CONNESSIONE
    	getConnection();
    }

    private void getConnection(){
        this.serverInterface = ServerInterface.getInstance();
    }

    public static Client getInstance(){
        if (client== null)
            client = new Client();
        return client;
    }

    public void sendToServer(NetMessage message){
    	serverInterface.getConnection().send(message);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    
    public void setUsername(String username){
    	this.username = username;
    }
    
    public void setId(Integer id){
    	this.id = id;
    }
    
    public String getUsername(){
    	return username;
    }
    
    public Integer getId(){
    	return id;
    }
}
