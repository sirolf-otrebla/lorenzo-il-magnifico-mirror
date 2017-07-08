package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class GameSetupMessage implements LobbyMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6590521318803258078L;
	private GameStatus status;

    public GameStatus getStatus() {
        return status;
    }

    public GameSetupMessage(GameStatus status) {
        this.status = status;
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
