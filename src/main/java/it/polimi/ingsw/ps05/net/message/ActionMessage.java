package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.controller.GameFlowController;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.server.PlayerClient;

public class ActionMessage implements NetMessage {
    private PlayerClient playerClient;
    private Player playerBefore;    // state before action
    private Action action;
    private int selectedOption = DEFAULT_OPTION;
    private static final int DEFAULT_OPTION = 0;


    public void acceptVisitor(GameFlowController gfc) throws Exception{
        gfc.visit(this);
    }

    public Player getPlayerBefore(){
        return playerBefore;
    }

    public Action getAction() {
        return action;
    }
    
    public void setSelectedOption(int selectedOption){
    	this.selectedOption = selectedOption;
    }
    
    public int getSelectedOption(){
    	return this.selectedOption;
    }
}
