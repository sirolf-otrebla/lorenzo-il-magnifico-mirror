package it.polimi.ingsw.ps05.server.controller;

import javafx.beans.Observable;

import java.util.Observer;

/**
 * Created by Alberto on 20/06/2017.
 */
public class LimitedBonusActListener implements Observer {
    private GameFlowController gfc;

    public LimitedBonusActListener(GameFlowController gfc){

        this.gfc = gfc;
    }

    @Override
    public void update(java.util.Observable o, Object arg) {

    }
}
