package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 07/07/2017.
 */
public interface GameResponseMessage extends NetMessage {

    public void acceptVisitor(GameResponseMessageVisitor vi);

}
