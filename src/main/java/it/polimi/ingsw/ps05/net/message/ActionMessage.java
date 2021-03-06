package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

public class ActionMessage implements GameMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2583147710479097672L;
    private int actionSpaceID;
    private ColorEnumeration familiarID;
    private Player playerBefore;
    private int selectedPayment = 0;


    public int getSelectedPayment() {
        return selectedPayment;
    }

    public ActionMessage( ColorEnumeration fam,
                         int actionSpace, int selectedPayment, Player playerBefore){
        this.actionSpaceID = actionSpace;
        this.familiarID = fam;
        this.selectedPayment = selectedPayment;
        this.playerBefore = playerBefore;
    }

    public Player getPlayerBefore() {
        return playerBefore;
    }

    public int getActionSpaceID() {
        return actionSpaceID;
    }

    public ColorEnumeration getFamiliarID() {
        return familiarID;
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
    	System.out.println("going to be visited by game command");
        vi.visit(this);
    }

    public void acceptVisitor(NetMessageVisitor vi) throws Exception{
        vi.visit(this);
    }
}
