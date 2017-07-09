package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 19/06/2017.
 */
public class Round {
    private ArrayList<Player> playerOrder;
    private Game game;
    private GameMessage inputMessage;
    private GameCommandsVisitor visitor;
    private Semaphore waitingMessageSemaphore;

    private int playerCounter;

    /* result listeners */
    private Iterator<Player> plOrdIt;

    public Round(ArrayList<Player> playerOrder, Game game){
        this.game = game;
        this.playerOrder = playerOrder;
        waitingMessageSemaphore = new Semaphore(0);

    }
    public void executeRound() throws InterruptedException {
        this.game.setState(this);
        plOrdIt = playerOrder.iterator();
        playerCounter = 0;
        this.game.setActivePlayer(playerOrder.get(playerCounter));
        do {
            game.getEndActionStrategyContainer().resetStrategy();
            game.getActivePlayer().evaluatePermanentEffects();
            Integer effectListSize = game.getActivePlayer().getPermanentEffectResList().size();
            game.getGameFlowctrl().sendUpdateMsg();
            this.waitCommand();
            this.executeCommand();
            game.getActivePlayer().resetPermanentEffects(effectListSize);
            game.getEndActionStrategyContainer().executeStrategy();
        } while (playerCounter < playerOrder.size());

    }
    private synchronized void waitCommand() throws InterruptedException {
        // send message
    	System.out.println("WAIT COMMAND");
        this.waitingMessageSemaphore.acquire();
    }

    private void executeCommand(){
        try {
            visitor = new GameCommandsVisitor(this.game.getActivePlayer(), this);
            this.inputMessage.acceptVisitor(visitor);
        } catch (Exception e) {

            //todo:
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }


    public void nextState() {
        Integer playerID = this.game.getActivePlayer().getPlayerID();
        this.game.getPlayerClient(playerID).setInactive();
        playerCounter++;
        if (playerCounter < playerOrder.size()) {
            game.setActivePlayer(this.playerOrder.get(playerCounter));
        }
        else {
            //TODO (MANCA QUALCOSA?)
            System.out.println("round finished");
        }
    }

    public void setInputMessage(GameMessage inputMessage) {
        this.inputMessage = inputMessage;
        waitingMessageSemaphore.release();
    }
}
