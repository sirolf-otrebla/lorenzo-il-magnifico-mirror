package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 08/07/2017.
 */
public class ExcommunicationChoiceMessage implements NetMessage {
    private  boolean acceptExcommunication;
    private ExcommunicationCard excommunicationCard;
    private Integer playerID;

    public ExcommunicationChoiceMessage(boolean acceptExcommunication, ExcommunicationCard excommunicationCard) {
        this.acceptExcommunication = acceptExcommunication;
        this.excommunicationCard = excommunicationCard;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    public boolean isAcceptExcommunication() {
        return acceptExcommunication;
    }

    public ExcommunicationCard getExcommunicationCard() {
        return excommunicationCard;
    }
}
