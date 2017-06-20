package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alberto on 19/06/2017.
 */
public class Round implements RoundInterface {
    private ArrayList<Player> playerOrder;
    private Game game;
    private NetMessage inputMessage;
    private GameCommandsVisitor visitor;

    /* result listeners */
    private Iterator<Player> plOrdIt;

    public Round(ArrayList<Player> playerOrder, Game game){
        this.game = game;
        this.playerOrder = playerOrder;
    }
    public void executeRound() throws InterruptedException {
        this.game.setState(this);
        plOrdIt = playerOrder.iterator();
        game.setActivePlayer(plOrdIt.next());
        while (plOrdIt.hasNext()) {
            game.getActivePlayer().evaluatePermanentEffects();
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

            //todo:
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }


    @Override
    public void nextState() {
        if (this.plOrdIt.hasNext())
        game.setActivePlayer(this.plOrdIt.next());

        else {
            //todo
            System.out.println("turn finished");
        }
    }
}
