package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 05/07/2017.
 */
public interface DraftMessage extends NetMessage {

    public void acceptVisitor(DraftVisitor vi);

}
