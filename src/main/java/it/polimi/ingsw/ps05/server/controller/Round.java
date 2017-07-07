package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameMessage;
import it.polimi.ingsw.ps05.net.message.GameUpdateMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

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
        this.game.setActivePlayer(plOrdIt.next());
        do {
            game.getEndActionStrategyContainer().resetStrategy();
            game.getActivePlayer().evaluatePermanentEffects();
            this.waitCommand();
            this.executeCommand();
            game.getEndActionStrategyContainer().executeStrategy();

        } while (plOrdIt.hasNext());

    }
    private synchronized void waitCommand() throws InterruptedException {
        // send message
    	System.out.println("WAIT COMMAND");
        game.getGameFlowctrl().sendUpdateMsg();
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
        if (this.plOrdIt.hasNext())
            game.setActivePlayer(this.plOrdIt.next());
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
