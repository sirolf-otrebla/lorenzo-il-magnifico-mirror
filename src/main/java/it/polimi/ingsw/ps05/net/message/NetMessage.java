package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public interface NetMessage {

    public void acceptVisitor(NetMessageVisitor vi) throws  Exception;


}
