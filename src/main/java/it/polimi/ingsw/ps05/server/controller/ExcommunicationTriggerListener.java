package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.net.message.gamemessages.ExcommunicationTriggerMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;

import java.util.Observable;
import java.util.Observer;

/**
 * this is a Listener Class, implementing the {@link Observer} interface, which is triggered when
 * an excommunication is in action. It is notified by the {@link TurnSetupManager} when there is a turn
 * change that triggers an Epoch change. the goal of this Listener is to send Excommunication messages
 * to the connected {@link PlayerClient} which are currently in game.
 *
 * @see ExcommunicationCard
 * @see it.polimi.ingsw.ps05.model.Turn
 * @see TurnSetupManager
 * @see Epoch
 *
 */
public class ExcommunicationTriggerListener implements Observer {

    private Game game;
    private GameFlowController gfc;

    /** main constructor for the ExcommunicationTriggerListener class
     *
     * @param gfc this is the {@link GameFlowController} reference, which is necessary to
     *            manipulate {@link PlayerClient} classes and {@link Game} class.
     */
    public ExcommunicationTriggerListener( GameFlowController gfc) {
        this.gfc = gfc;
        this.game = gfc.getGame();
    }

    /** this method is triggered by a {@code notify} called by {@link TurnSetupManager} when
     * an Excommunication is triggered (i.e. when the epoch changes)
     * @param o this is the observable that's calling the listener
     * @param arg this is a generic arg for the listener, in this implementation it represents the Epoch
     *            that contains the proper Excommunication Card.
     * @see Epoch
     * @see ExcommunicationCard
     */
    @Override
    public void update(Observable o, Object arg) {
        Epoch epoch = (Epoch) arg;
        ExcommunicationCard ex = epoch.getExcomunicationCard();
        for (PlayerClient pl: this.game.getPlayerInGame().values()) {
            FaithResource faith = (FaithResource) pl.getPlayer().getResource(FaithResource.id);
            ExcommunicationTriggerMessage message;
            if (ex.getFaithRequested().getValue() > faith.getValue()) {
                ex.applyEffect(pl.getPlayer());
                 message = new ExcommunicationTriggerMessage(ExcommunicationTriggerMessage.EXCOMMUNICATED, ex);
            } else {
                 message = new ExcommunicationTriggerMessage(ExcommunicationTriggerMessage.CHOICE, ex);
            }
            pl.sendMessage(message);

        }

    }
}
