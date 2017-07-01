package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.interfaces.HarvestSpaceViewObject;
import it.polimi.ingsw.ps05.client.view.interfaces.ProductionSpaceViewObject;
import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.HarvestActionMessage;
import it.polimi.ingsw.ps05.net.message.ProductionActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 29/06/2017.
 */
public class HarvestActionListener implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        HarvestSpaceViewObject spaceViewObject = (HarvestSpaceViewObject) arg;
        ActionMessage actionMessage = new ActionMessage(spaceViewObject.getFamilyMemberID(),spaceViewObject.getId(),
                0, Client.getInstance().getGameStatus().getThisPlayer());
        HarvestActionMessage harvestActionMessage = new HarvestActionMessage(actionMessage, spaceViewObject.getActiveCardsId());

    }
}
