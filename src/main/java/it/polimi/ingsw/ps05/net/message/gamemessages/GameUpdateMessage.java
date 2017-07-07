package it.polimi.ingsw.ps05.net.message.gamemessages;


import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 12/06/2017.
 */
public class GameUpdateMessage implements GameResponseMessage {

    public static final String MESSAGE = "GameUpdateMessage Here!";
    private GameStatus gameStatus;

    public GameUpdateMessage(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameResponseMessageVisitor vi) {
        vi.visit(this);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String toString(){
        return MESSAGE;
    }
}
