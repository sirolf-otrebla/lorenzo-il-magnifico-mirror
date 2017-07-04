package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.LobbyResponseMessageVisitor;

/**
 * Created by Alberto on 04/07/2017.
 */
public interface LobbyResponseMessage extends NetMessage {

    public void acceptVisitor(LobbyResponseMessageVisitor vi);
}
