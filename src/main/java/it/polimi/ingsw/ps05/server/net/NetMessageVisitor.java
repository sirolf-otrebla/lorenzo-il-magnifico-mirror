package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;
import it.polimi.ingsw.ps05.net.message.GameMessage;
import it.polimi.ingsw.ps05.net.message.LobbyMessage;

/**
 * Created by Alberto on 14/06/2017.
 */
public interface NetMessageVisitor {

    void visit(GameMessage msg);

    void visit(LobbyMessage msg);


}
