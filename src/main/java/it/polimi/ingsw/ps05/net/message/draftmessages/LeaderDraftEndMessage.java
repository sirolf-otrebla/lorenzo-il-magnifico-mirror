package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.net.message.draftmessages.LeaderDraftMessage;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class LeaderDraftEndMessage implements LeaderDraftMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -620774875766220141L;
	private ArrayList<Integer> playerLeaderCards;

    public LeaderDraftEndMessage(ArrayList<Integer> playerLeaderCards){

        this.setPlayerLeaderCards(playerLeaderCards);
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

	@Override
	public void acceptVisitor(DraftVisitor vi) {
		// TODO Auto-generated method stub
		vi.visit(this);
		
	}

	public ArrayList<Integer> getPlayerLeaderCards() {
		return playerLeaderCards;
	}

	public void setPlayerLeaderCards(ArrayList<Integer> playerLeaderCards) {
		this.playerLeaderCards = playerLeaderCards;
	}
}
