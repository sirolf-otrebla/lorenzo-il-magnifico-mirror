package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class EndDraftMessage implements LeaderDraftMessage {


    private ArrayList<Integer> playerLeaderCards;

    public EndDraftMessage(ArrayList<Integer> playerLeaderCards){

        this.playerLeaderCards = playerLeaderCards;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(DraftVisitor vi) {

    }
}
