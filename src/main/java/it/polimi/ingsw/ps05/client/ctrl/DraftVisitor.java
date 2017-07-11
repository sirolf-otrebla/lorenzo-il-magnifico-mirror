package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.View;
import it.polimi.ingsw.ps05.net.message.draftmessages.*;

/**
 * Created by Alberto on 05/07/2017.
 */
public class DraftVisitor {

    public void visit(StartLeaderDraftMessage msg){
        ViewAdapter.getInstance().startLeaderDraft(msg.getLeaderReferenceIdList());

    }

    public void visit(LeaderDraftEndMessage msg){
        ViewAdapter.getInstance().endLeaderDraft(msg);
    }

    public void visit(LeaderDraftUpdateNetMessage msg){
        ViewAdapter.getInstance().updateLeaderDraft(msg.getLeaderCardArrayList());
    }

    public void visit(BonusTileDraftUpdateNetMessage msg){
        ViewAdapter.getInstance().updateBonusTileDraft(msg.getIdArrayList());
    }

    public void visit(StartBonusTileDraftMessage msg){
        ViewAdapter.getInstance().startBonusTileDraft(msg.getIdArrayList());
    }

    public void visit(BonusTileDraftEndMessage msg){
        ViewAdapter.getInstance().endBonusTileDraft();
    }

}
