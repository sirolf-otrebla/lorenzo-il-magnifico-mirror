package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.resourcesandbonuses.PermanentBonus;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alberto on 19/06/2017.
 */
public class Round {
    private ArrayList<Player> playerOrder;
    private Game game;
    private NetMessage inputMessage;
    private GameCommandsVisitor visitor;

    private BonusActionListener bonusActListener;
    private Iterator<Player> plOrdIt;
    private ExcommunicationTriggerListener exTrigger;

    private Player activePlayer;
    public Round(ArrayList<Player> playerOrder, Game game){
        this.game = game;
        this.playerOrder = playerOrder;
        this.bonusActListener = new BonusActionListener(this);
    }
    public void executeRound() throws InterruptedException {
        plOrdIt = playerOrder.iterator();
        activePlayer = plOrdIt.next();
        while (plOrdIt.hasNext()) {
            activePlayer.evaluatePermanentEffects();
            this.waitCommand();
            this.executeCommand();

        }

    }
    private void waitCommand() throws InterruptedException {
        if (this.inputMessage == null) wait();

    }

    private void executeCommand(){
        try {
            this.inputMessage.acceptVisitor(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
}
