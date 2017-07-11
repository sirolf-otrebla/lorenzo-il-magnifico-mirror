package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 * this class represents a Round. in this context a round is not what it is in the official game rules:
 * what is called round from the official rules in this implementation is called Turn. Round is, as the word
 * suggest, a simple sequence of actions that starts and ends with the same player. 4 rounds compose a Turn.
 * 2 Turns compose an epoch
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

    /**
     * default constructor for Random Class. needs the player Order and the game
     * @param playerOrder player order (a list where the player with lowest index is the 1st to play.
     * @param game a reference to te related game
     */
    public Round(ArrayList<Player> playerOrder, Game game){
        this.game = game;
        this.playerOrder = playerOrder;
        waitingMessageSemaphore = new Semaphore(0);

    }

    /**
     * execute a round as described in class Desciption.
     * @throws InterruptedException thrown when the thread that hosts this method is interrupted.
     */
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
            game.getStartActionStrategyContainer().executeStrategy();
            this.waitCommand();
            this.executeCommand();
            game.getActivePlayer().resetPermanentEffects(effectListSize);
            game.getEndActionStrategyContainer().executeStrategy();
            game.getStartActionStrategyContainer().resetStrategy();
        } while (playerCounter < playerOrder.size());

    }

    /**
     * here the round waits the active player to send a proper {@link GameMessage}. if the Server catch a message
     * from the network {@code waitingMessageSemaphore} is released.
     * @throws InterruptedException
     */
    private synchronized void waitCommand() throws InterruptedException {
        // send message
    	System.out.println("WAIT COMMAND");
    	System.out.println(waitingMessageSemaphore.availablePermits());
        this.waitingMessageSemaphore.acquire();
    }

    /**
     * here the round executes the command sent by the client, using a GameCommandsVisitor object
     * @see GameCommandsVisitor
     */
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


    /**
     *  moves the round state to the next player. if there are no more players, the round ends.
     */
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
    
    

    /** receives a {@link GameMessage} as a input parameter, and release the waiting semaphore.
     *
     * @param inputMessage an input message from the Client
     */
    public void setInputMessage(GameMessage inputMessage) {
        this.inputMessage = inputMessage;
        waitingMessageSemaphore.release();
    }
}
