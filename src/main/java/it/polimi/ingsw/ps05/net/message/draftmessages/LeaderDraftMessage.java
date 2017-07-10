package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;

/**
 * Created by Alberto on 02/07/2017.
 */
public interface LeaderDraftMessage extends NetMessage {

    public void acceptVisitor(DraftVisitor vi);
}
