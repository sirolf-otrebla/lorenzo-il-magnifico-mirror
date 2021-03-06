package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.interfaces.TowerTileViewObject;
import it.polimi.ingsw.ps05.net.message.ActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class TakeCardListener implements Observer{


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("arrivato all'observer TakeCardListener");
        TowerTileViewObject tileWidget = (TowerTileViewObject) arg;
        if (tileWidget.hasMorePaymentOptions()) {
        	System.out.println("writing message ActionMessage");
            ActionMessage msg = new ActionMessage(tileWidget.getFamilyMemberID(), tileWidget.getId(),
                    tileWidget.getSelectedPayment(), Client.getInstance().getGameStatus().getThisPlayer());
            Client.getInstance().sendToServer(msg);
        }
        else {
            System.out.println("writing message ActionMessage");
            ActionMessage msg = new ActionMessage(tileWidget.getFamilyMemberID(), tileWidget.getId(),
                    0, Client.getInstance().getGameStatus().getThisPlayer());
            Client.getInstance().sendToServer(msg);
        }


    }
}
