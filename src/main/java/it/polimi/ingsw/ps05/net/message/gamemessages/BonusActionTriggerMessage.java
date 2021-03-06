package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.client.ctrl.GameResponseMessageVisitor;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 12/06/2017.
 */
public class BonusActionTriggerMessage implements GameResponseMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7588254960030498162L;
	private GameUpdateMessage gameUpdateMessage;
    private Familiar ghostFamiliar;


    private ColorEnumeration actionColor;

    /**
     *
     * @param actionColor       if NOT_INIZIALIZED: FREE ACTION, IF ANY: FREE ACTION ONLY ON TOWERS
     *                          ANyTHING ELSE: FREE ACTION UNDER THE TOWER WITH THIS COLOR
     * @param gameUpdateMessage
     * @param ghostFamiliar
     */
    public BonusActionTriggerMessage(ColorEnumeration actionColor, GameUpdateMessage gameUpdateMessage, Familiar ghostFamiliar){
        this.gameUpdateMessage = gameUpdateMessage;
        this.ghostFamiliar = ghostFamiliar;
        this.actionColor = actionColor;
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

    public Familiar getGhostFamiliar() {
        return ghostFamiliar;
    }
    
    public ColorEnumeration getActionColor(){
    	return actionColor;
    }

}
