package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class StartLeaderDraftMessage implements DraftMessage {

    private ArrayList<Integer> leaderReferenceIdList;

    public StartLeaderDraftMessage(ArrayList<Integer> leaderReferenceIdList){
        this.leaderReferenceIdList = leaderReferenceIdList;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) {
        vi.visit(this);
    }

    public ArrayList<Integer> getLeaderReferenceIdList() {
        return leaderReferenceIdList;
    }

    @Override
    public void acceptVisitor(DraftVisitor vi) {
        vi.visit(this);
    }
}
