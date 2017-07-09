package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 07/07/2017.
 */
public class  ExcommunicationTriggerMessage implements GameResponseMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9112577305094910932L;
	public static final int CHOICE = 1;
    public static final int EXCOMMUNICATED = 0;

    private ExcommunicationCard excommunicationCard;
    private int state;

    public ExcommunicationTriggerMessage(int state, ExcommunicationCard excommunicationCard) {
        this.state = state;
        this.excommunicationCard = excommunicationCard;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameResponseMessageVisitor vi) {
        vi.visit(this);
    }

    public int getState() {
        return state;
    }

    public ExcommunicationCard getExcommunicationCard() {
        return excommunicationCard;
    }
}
