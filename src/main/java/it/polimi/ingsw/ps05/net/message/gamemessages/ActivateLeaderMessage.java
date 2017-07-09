package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class ActivateLeaderMessage implements GameMessage {
	
	private static final long serialVersionUID = 3178536392505445475L;

	private Integer leaderCardReferenceID;

    private Player playerStatus;

    public ActivateLeaderMessage(Integer leaderCardReferenceID, Player playerStatus) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptVisitor(GameCommandsVisitor vi) {
		// TODO Auto-generated method stub
	}

}
