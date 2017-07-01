package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.interfaces.HarvestSpaceViewObject;
import it.polimi.ingsw.ps05.client.view.interfaces.ProductionSpaceViewObject;
import it.polimi.ingsw.ps05.model.spaces.ProductionSpace;
import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.HarvestActionMessage;
import it.polimi.ingsw.ps05.net.message.ProductionActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ProductionActionListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ProductionSpaceViewObject spaceViewObject = (ProductionSpaceViewObject) arg;
        ActionMessage actionMessage = new ActionMessage(spaceViewObject.getFamilyMemberID(),spaceViewObject.getId(),
                0, Client.getInstance().getGameStatus().getThisPlayer());
        ProductionActionMessage productionActionMessage = new ProductionActionMessage(actionMessage, spaceViewObject.getActiveCardsId());

    }
}
