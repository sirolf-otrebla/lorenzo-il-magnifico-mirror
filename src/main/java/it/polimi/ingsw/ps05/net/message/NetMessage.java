package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.controller.GameFlowController;
import it.polimi.ingsw.ps05.net.server.NetMessageVisitor;

public interface NetMessage {

    public void acceptVisitor(NetMessageVisitor vi) throws  Exception;


}
