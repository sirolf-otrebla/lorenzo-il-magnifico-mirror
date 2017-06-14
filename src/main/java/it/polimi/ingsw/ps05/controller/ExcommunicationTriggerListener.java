package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.ExcommunicationCard;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.net.server.PlayerClient;
import it.polimi.ingsw.ps05.resourcesandbonuses.FaithResource;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 14/06/2017.
 */
public class ExcommunicationTriggerListener implements Observer {

    private Game game;
    private GameFlowController gfc;

    public ExcommunicationTriggerListener( GameFlowController gfc) {
        this.gfc = gfc;
    }

    @Override
    public void update(Observable o, Object arg) {
        Epoch epoch = (Epoch) arg;
        ExcommunicationCard ex = epoch.getExcomunicationCard();
        for (PlayerClient pl: this.game.getPlayerInGame()){
            FaithResource faith = (FaithResource) pl.getPlayer().getResource(FaithResource.id);
            if (ex.getFaithRequested().getValue() < faith.getValue())
                ex.applyEffect(pl.getPlayer());
        }

    }
}
