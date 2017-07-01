package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class GameSetupMessage implements GameMessage {

    private GameStatus status;


    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
