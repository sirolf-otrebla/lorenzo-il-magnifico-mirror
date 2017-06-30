package it.polimi.ingsw.ps05.client.net;

/**
 * Created by Alberto on 26/06/2017.
 */
public class ServerInterface {

    private Connection connection;
    public static ServerInterface serverInterface;

    private ServerInterface(){
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


}
