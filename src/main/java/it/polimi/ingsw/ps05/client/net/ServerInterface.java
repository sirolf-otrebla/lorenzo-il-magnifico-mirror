package it.polimi.ingsw.ps05.client.net;

/**
 * Created by Alberto on 26/06/2017.
 */
public class ServerInterface {

    private String hostUrl;
    public static ServerInterface serverInterface;

    private ServerInterface(){
    }

    public static ServerInterface getInstance(){
        if (serverInterface == null) return new ServerInterface();
        else return  serverInterface;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }


}
