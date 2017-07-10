package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ProductionActionMessage implements GameMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3152205493702020645L;
	private ActionMessage actionMessage;
    private ArrayList<Integer> activeCardsIds;
    private ArrayList<Integer> activateEffectForCard;

    public ProductionActionMessage(ActionMessage msg, ArrayList<Integer> activeCardsIds, ArrayList<Integer> activateEffectForCard){
        this.actionMessage = msg;
        this.activeCardsIds = activeCardsIds;
        this.activateEffectForCard = activateEffectForCard;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    public ActionMessage getActionMessage() {
        return actionMessage;
    }

    public ArrayList<Integer> getActiveCardsIds() {
        return activeCardsIds;
    }
    
    public ArrayList<Integer> optionForCard(){
    	return this.activateEffectForCard;
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
        vi.visit(this);
    }
}
