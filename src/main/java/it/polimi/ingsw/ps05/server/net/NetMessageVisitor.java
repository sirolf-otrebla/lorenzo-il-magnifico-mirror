package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.net.message.gamemessages.ExcommunicationChoiceMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameResponseMessage;

/**
 * Created by Alberto on 14/06/2017.
 */
public interface NetMessageVisitor {

    void visit(GameResponseMessage msg);

    void visit(GameMessage msg);

    void visit(LobbyMessage msg);

    void visit(AuthMessage msg);

    void visit(AuthResponseMessage msg);

    void visit(LobbyResponseMessage msg);

    void visit(DraftMessage msg);

    void visit(DraftResponseNetMessage msg);

    void visit(ExcommunicationChoiceMessage msg);




}
