package it.polimi.ingsw.ps05.server.controller.actionlisteners;


import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.controller.GameFlowController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 13/06/2017.
 */
public class EndActionListener implements Observer {
    private GameFlowController gameFlowController;

    public EndActionListener(GameFlowController gameFlowController){
        this.gameFlowController = gameFlowController;
    }

    @Override
    public void update(Observable o, Object arg) {
        ActionResult res = (ActionResult) o;
        this.gameFlowController.getGame().getState().nextState();
        this.gameFlowController.sendUpdateMsg();

    }
}
