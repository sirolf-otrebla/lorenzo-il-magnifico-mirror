package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.LeaderDraftUpdateNetMessage;
import it.polimi.ingsw.ps05.net.message.StartLeaderDraftMessage;

/**
 * Created by Alberto on 05/07/2017.
 */
public class DraftVisitor {

    public void visit(StartLeaderDraftMessage msg){
        ViewAdapter.getInstance().startDraft(msg.getLeaderReferenceIdList());

    }

    public void visit(LeaderDraftUpdateNetMessage msg){
        ViewAdapter.getInstance().updateDraft(msg.getLeaderCardArrayList());
    }

}
