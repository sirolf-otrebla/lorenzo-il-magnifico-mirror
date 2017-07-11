package it.polimi.ingsw.ps05.client.net;

import it.polimi.ingsw.ps05.client.ctrl.Client;

/**
 * Created by Alberto on 26/06/2017.
 */
public class ServerInterface implements Runnable{

    private Connection connection;
    private static ServerInterface serverInterface;

    private ServerInterface() {
    }

    public static ServerInterface getInstance(){
        if (serverInterface == null) serverInterface = new ServerInterface();
        return  serverInterface;
    }

    public void setConnection(Connection connection) {
        ;
        this.connection = connection;
        System.out.println("connessione registrata");
    }
    
    public Connection getConnection(){
    	return connection;
    }


    @Override
    public void run() {
        Thread thread = new Thread(Client.getInstance().getMessageTaker());
        thread.start();
        this.connection.listen();
    }
}
