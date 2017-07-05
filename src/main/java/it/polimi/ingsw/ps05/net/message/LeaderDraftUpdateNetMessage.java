package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class LeaderDraftUpdateNetMessage implements DraftMessage {

    private ArrayList<Integer> leaderCardArrayList;
    public LeaderDraftUpdateNetMessage(ArrayList<Integer> leaderCardsReferenceIds){
        leaderCardArrayList = leaderCardsReferenceIds;
    }

    public ArrayList<Integer> getLeaderCardArrayList() {
        return leaderCardArrayList;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(DraftVisitor vi) {
        vi.visit(this);
    }
}
