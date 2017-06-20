package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class ActionMessage implements NetMessage {
    private PlayerClient playerClient;
    private Player playerBefore;    // state before action
    private ActionSpace actionSpace;
    private Familiar familiar;
    private int selectedPayment = 0;



    public void acceptVisitor(NetMessageVisitor vi) throws Exception{
        vi.visit(this);
    }

    public int getSelectedPayment() {
        return selectedPayment;
    }

    public ActionMessage(Player before, Familiar fam,
                         ActionSpace actionSpace, int selectedPayment){
        playerBefore = before;
        this.actionSpace = actionSpace;
        this.familiar = fam;
        this.selectedPayment = selectedPayment;
    }

    public Player getPlayerBefore(){
        return playerBefore;
    }

    public ActionSpace getActionSpace() {
        return actionSpace;
    }

    public PlayerClient getPlayerClient() {
        return playerClient;
    }

    public void setPlayerClient(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    public Familiar getFamiliar() {
        return familiar;
    }
    
}
