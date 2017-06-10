package it.polimi.ingsw.ps05.net.server;


import it.polimi.ingsw.ps05.net.server.Message.NetMessage;

import java.util.Observable;

/**
 * Created by Alberto on 08/06/2017.
 */
public abstract class LimConnection extends Observable {

    public abstract  void listen();

    public abstract void send(NetMessage mess);

    public abstract void flushInBuf();

    public abstract NetMessage getInputMessage();


}
