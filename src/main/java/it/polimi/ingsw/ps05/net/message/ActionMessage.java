package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.controller.GameFlowController;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.server.PlayerClient;

public class ActionMessage implements NetMessage {
    private PlayerClient playerClient;
    private Player playerBefore;    // state before action
    private Action action;


    public void acceptVisitor(GameFlowController gfc) throws Exception{
        gfc.visit(this);
    }

    public Player getPlayerBefore(){
        return playerBefore;
    }

    public Action getAction() {
        return action;
    }
}
