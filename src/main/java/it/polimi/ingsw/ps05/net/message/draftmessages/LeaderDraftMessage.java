package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 02/07/2017.
 */
public interface LeaderDraftMessage extends NetMessage {

    public void acceptVisitor(DraftVisitor vi);
}
