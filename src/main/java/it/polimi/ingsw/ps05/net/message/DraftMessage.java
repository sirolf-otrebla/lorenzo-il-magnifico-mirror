package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
/**
 * Created by Alberto on 05/07/2017.
 */
public interface DraftMessage extends NetMessage {

    public void acceptVisitor(DraftVisitor vi);

}
