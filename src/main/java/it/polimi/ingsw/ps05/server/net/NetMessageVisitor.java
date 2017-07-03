package it.polimi.ingsw.ps05.server.net;

import it.polimi.ingsw.ps05.net.message.*;

/**
 * Created by Alberto on 14/06/2017.
 */
public interface NetMessageVisitor {

    void visit(GameMessage msg);

    void visit(LobbyMessage msg);

    void visit(LeaderDraftMessage msg);

    void visit(AuthMessage msg);

    void visit(AuthResponseMessage msg);

}
