package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.net.message.DraftResponseNetMessage;
import it.polimi.ingsw.ps05.server.controller.DraftResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 10/07/2017.
 */
public class BonusTileDraftChoiceMessage implements DraftResponseNetMessage {
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        
    }

    @Override
    public void acceptVIsitor(DraftResponseMessageVisitor vi) {

    }
}
