package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.TowerTileWidget;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.net.message.ActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class TakeCardListener implements Observer{


    @Override
    public void update(Observable o, Object arg) {
        TowerTileWidget tileWidget = (TowerTileWidget) arg;
        if (tileWidget.hasMorePaymentOptions()) {
            //TODO FINESTRA SELEZIONE
        }
        else {
            ActionMessage msg = new ActionMessage(tileWidget.getFamilyMemberID(), tileWidget.getId(),
                    0, Client.getInstance().getGameStatus().getThisPlayer());
            Client.getInstance().sendToServer(msg);
        }


    }
}
