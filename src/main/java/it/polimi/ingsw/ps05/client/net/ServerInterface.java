package it.polimi.ingsw.ps05.client.net;

import it.polimi.ingsw.ps05.client.ctrl.Client;

/**
 * Created by Alberto on 26/06/2017.
 */
public class ServerInterface implements Runnable{

    private Connection connection;
    public static ServerInterface serverInterface;

    private ServerInterface() {
    }

    public static ServerInterface getInstance(){
        if (serverInterface == null) return new ServerInterface();
        else return  serverInterface;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Connection getConnection(){
    	return connection;
    }


    @Override
    public void run() {
        Thread diocan = new Thread(Client.getInstance().getMessageTaker());
        diocan.start();
        this.connection.listen();
    }
}
