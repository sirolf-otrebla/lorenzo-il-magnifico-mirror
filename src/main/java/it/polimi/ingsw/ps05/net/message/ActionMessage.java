package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

public class ActionMessage implements NetMessage {
    private PlayerClient playerClient;
    private int actionSpaceID;
    private ColorEnumeration familiarID;
    private int selectedPayment = 0;



    public void acceptVisitor(NetMessageVisitor vi) throws Exception{
        vi.visit(this);
    }

    public int getSelectedPayment() {
        return selectedPayment;
    }

    public ActionMessage( ColorEnumeration fam,
                         int actionSpace, int selectedPayment){
        this.actionSpaceID = actionSpace;
        this.familiarID = fam;
        this.selectedPayment = selectedPayment;
    }
    
}
