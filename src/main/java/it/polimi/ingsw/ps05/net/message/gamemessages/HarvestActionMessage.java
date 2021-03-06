package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 29/06/2017.
 */
public class HarvestActionMessage implements GameMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5734101287044624912L;
	private ActionMessage actionMessage;
    private ArrayList<Integer> activeCardsIds;
    private ArrayList<Integer> effectToActivateForCard;

    public HarvestActionMessage(ActionMessage msg, ArrayList<Integer> activeCardsIds, ArrayList<Integer> effectToActivateForCard){
        this.actionMessage = msg;
        this.activeCardsIds = activeCardsIds;
        this.effectToActivateForCard = effectToActivateForCard;
    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }
    
    public ArrayList<Integer> getEffectToActivateForCards(){
    	return this.effectToActivateForCard;
    }

    public ActionMessage getActionMessage() {
        return actionMessage;
    }

    public ArrayList<Integer> getActiveCardsIds() {
        return activeCardsIds;
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
    	//TODO sono state implementate le scelte degli effetti
        vi.visit(this);
    }

    // decoratore di ActionMessage
}
