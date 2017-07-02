package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class DraftResponseMessage implements LeaderDraftMessage {

    private ArrayList<Integer> leaderCardArrayList;
    public DraftResponseMessage(ArrayList<Integer> leaderCardsReferenceIds){
        leaderCardArrayList = leaderCardsReferenceIds;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }
}
