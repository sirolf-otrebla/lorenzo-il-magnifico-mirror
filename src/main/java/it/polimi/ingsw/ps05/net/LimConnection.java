package it.polimi.ingsw.ps05.net;



import java.util.Observable;

import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 08/06/2017.
 */
public abstract class LimConnection extends Observable {

    public abstract void listen();
    
    public abstract void send(NetMessage mess);
    
    public abstract void flushInBuff();
    
    public abstract NetMessage getInputMessage();

    public abstract void flushInBuf();


}
