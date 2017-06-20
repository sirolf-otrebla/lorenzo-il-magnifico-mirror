package it.polimi.ingsw.ps05.net.message;


import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 12/06/2017.
 */
public class UpdateMessage implements NetMessage{

    private Action act;
    private Player playerAfter;
    private Player nextPlayer;

    public  UpdateMessage(Action act, Player playerAfter, Player nextPlayer){
        this.act = act;
        this.playerAfter = playerAfter;
        this.nextPlayer = nextPlayer;

    }
    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {

    }
}
