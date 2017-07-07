package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;

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
        this.game = gfc.getGame();
    }

    @Override
    public void update(Observable o, Object arg) {
        Epoch epoch = (Epoch) arg;
        ExcommunicationCard ex = epoch.getExcomunicationCard();
        for (PlayerClient pl: this.game.getPlayerInGame().values()){
            FaithResource faith = (FaithResource) pl.getPlayer().getResource(FaithResource.ID);
            if (ex.getFaithRequested().getValue() < faith.getValue());
              ex.applyEffect(pl.getPlayer());
        }

    }
}
