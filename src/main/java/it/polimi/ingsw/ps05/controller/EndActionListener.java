package it.polimi.ingsw.ps05.controller;


import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 13/06/2017.
 */
public class EndActionListener implements Observer {
    private GameFlowController gfc;

    public EndActionListener(GameFlowController gfc){
        this.gfc = gfc;
    }

    @Override
    public void update(Observable o, Object arg) {
        ActionResult res = (ActionResult) o;
        gfc.setNextPlayer();
        //TODO:

    }
}
