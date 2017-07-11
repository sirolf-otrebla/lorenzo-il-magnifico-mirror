package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.server.controller.DraftResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 10/07/2017.
 */
public class BonusTileDraftChoiceMessage implements DraftResponseNetMessage {

    private Integer choice;

    public BonusTileDraftChoiceMessage(Integer choice){
        this.choice = choice;
    }

    public Integer getChoice() {
        return choice;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }


    @Override
    public void acceptVIsitor(DraftResponseMessageVisitor vi) {
        vi.visit(this);
    }
}
