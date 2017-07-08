package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 07/07/2017.
 */
public class BonusProductionTriggerMessage implements GameResponseMessage {


    private GameUpdateMessage gameUpdateMessage;
    private String description = "hai una azione produzione bonus";

    public BonusProductionTriggerMessage(GameUpdateMessage gameUpdateMessage) {
        this.gameUpdateMessage = gameUpdateMessage;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameResponseMessageVisitor vi) {
        vi.visit(this);
    }

    public GameUpdateMessage getGameUpdateMessage() {
        return gameUpdateMessage;
    }

    public String getDescription() {
        return description;
    }
}
