package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 03/07/2017.
 */
public class EnteringLobbyMessage implements LobbyMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6751354096571951579L;

	private boolean youOrOthers;

    private ArrayList<String> PlayerUsernamesList;

    public EnteringLobbyMessage(boolean youOrOthers, ArrayList<String> playerUsernamesList) {
        this.youOrOthers = youOrOthers;
        PlayerUsernamesList = playerUsernamesList;
    }

    public boolean isYouOrOthers() {
        return youOrOthers;
    }

    public ArrayList<String> getPlayerUsernamesList() {
        return PlayerUsernamesList;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(LobbyMessageVisitor vi) {
        vi.visit(this);
    }
}
