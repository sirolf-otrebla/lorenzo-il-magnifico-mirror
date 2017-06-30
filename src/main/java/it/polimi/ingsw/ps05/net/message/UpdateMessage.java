package it.polimi.ingsw.ps05.net.message;


import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 12/06/2017.
 */
public class UpdateMessage implements GameMessage{

    private GameStatus gameStatus;

    public UpdateMessage(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {

    }
}
