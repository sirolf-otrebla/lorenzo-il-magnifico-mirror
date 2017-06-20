package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;

/**
 * Created by Alberto on 14/06/2017.
 */
public interface NetMessageVisitor {

    void visit(ActionMessage msg);

    void visit(ExitGameMessage mess);


}
