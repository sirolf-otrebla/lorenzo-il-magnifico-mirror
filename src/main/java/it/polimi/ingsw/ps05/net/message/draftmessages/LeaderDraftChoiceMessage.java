package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.net.message.draftmessages.DraftResponseNetMessage;
import it.polimi.ingsw.ps05.server.controller.DraftResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 02/07/2017.
 */
public class LeaderDraftChoiceMessage implements DraftResponseNetMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3208852752070619956L;
	private Integer choice;

    public LeaderDraftChoiceMessage(Integer choice){
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
