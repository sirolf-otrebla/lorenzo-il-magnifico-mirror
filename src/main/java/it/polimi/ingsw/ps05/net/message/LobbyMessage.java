package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public interface LobbyMessage extends NetMessage {

    public void acceptVisitor(LobbyMessageVisitor vi);
}
