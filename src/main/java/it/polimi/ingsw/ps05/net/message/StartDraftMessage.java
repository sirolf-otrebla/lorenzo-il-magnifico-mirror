package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class StartDraftMessage implements LeaderDraftMessage {

    private ArrayList<Integer> leaderReferenceIdList;

    public StartDraftMessage(ArrayList<Integer> leaderReferenceIdList){
        this.leaderReferenceIdList = leaderReferenceIdList;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    public ArrayList<Integer> getLeaderReferenceIdList() {
        return leaderReferenceIdList;
    }
}
