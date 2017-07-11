package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.controller.DraftResponseMessageVisitor;

/**
 * Created by Alberto on 05/07/2017.
 */
public interface DraftResponseNetMessage extends NetMessage {

    public void acceptVIsitor(DraftResponseMessageVisitor vi);
}
