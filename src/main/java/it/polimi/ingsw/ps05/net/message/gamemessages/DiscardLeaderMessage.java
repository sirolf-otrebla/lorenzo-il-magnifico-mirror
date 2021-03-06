package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 29/06/2017.
 */
public class DiscardLeaderMessage implements GameMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3178536392505445475L;

	private Integer leaderCardReferenceID;

    private Player playerStatus;

    public DiscardLeaderMessage(Integer leaderCardReferenceID, Player playerStatus) {
        this.leaderCardReferenceID = leaderCardReferenceID;
        this.playerStatus = playerStatus;
    }

    public Integer getLeaderCardReferenceID() {
        return leaderCardReferenceID;
    }

    public Player getPlayerStatus() {
        return playerStatus;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
    	vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
    	vi.visit(this);
    }
}
