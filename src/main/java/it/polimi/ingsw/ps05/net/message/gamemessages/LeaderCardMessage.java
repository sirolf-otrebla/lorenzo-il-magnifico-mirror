package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 14/06/2017.
 */
public class LeaderCardMessage implements GameMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6289721597274255922L;
	public static final int TYPE_DISCARD = 0;
    public static final int TYPE_ACTIVATE = 1;

    private int[] effectChoice;
    private LeaderCard leaderCard;
    private Player pl;

    private int msgType;

    public LeaderCardMessage(LeaderCard leaderCard, Player pl, int msgType, int[] effectChoice) {
        this.effectChoice = effectChoice;
        this.leaderCard = leaderCard;
        this.pl = pl;
        this.msgType = msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }

    public Player getPl() {
        return pl;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }

    public int[] getEffectChoice() {
        return effectChoice;
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
        vi.visit(this);
    }
}