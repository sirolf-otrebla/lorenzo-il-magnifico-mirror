package it.polimi.ingsw.ps05.net.message;


import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameResponseMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 12/06/2017.
 */
public class GameUpdateMessage implements GameResponseMessage{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8954796480135560515L;
	public static final String MESSAGE = "GameUpdateMessage Here!";
    private GameStatus gameStatus;

    public GameUpdateMessage(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String toString(){
        return MESSAGE;
    }
	@Override
	public void acceptVisitor(GameResponseMessageVisitor vi) {
		vi.visit(this);
		
	}
}
