package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public interface GameMessage extends NetMessage {

    public void acceptVisitor(GameCommandsVisitor vi);
}
