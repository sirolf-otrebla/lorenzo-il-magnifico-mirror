package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.io.Serializable;

public interface NetMessage extends Serializable {

    public void acceptVisitor(NetMessageVisitor vi) throws  Exception;


}
