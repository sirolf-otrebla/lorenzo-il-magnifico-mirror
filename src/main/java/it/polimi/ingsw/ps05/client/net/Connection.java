package it.polimi.ingsw.ps05.client.net;

import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 26/06/2017.
 */
public abstract class Connection {

	public abstract void listen();

	public abstract void send(NetMessage mess);

	public abstract void flushInBuff();

	public abstract NetMessage getInputMessage();

	public abstract void flushInBuf();

}
