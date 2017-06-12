package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.controller.GameFlowController;

public interface NetMessage {

    public void acceptVisitor(GameFlowController gfc) throws  Exception;


}
