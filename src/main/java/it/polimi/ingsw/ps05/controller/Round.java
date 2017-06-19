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


    public Round(ArrayList<Player> playerOrder, Game game){
        this.game = game;
        this.playerOrder = playerOrder;
    }
    public void ExecuteRound(){
        try {
            Iterator<Player> iterator = playerOrder.iterator();
            Player activePlayer = iterator.next();
            while (iterator.hasNext()) {
                activePlayer.evaluatePermanentEffects();
                this.waitCommand();
                this.executeCommand();
            }
        } catch (InterruptedException e){
            //TODO
        }
    }

    private void waitCommand()throws InterruptedException{
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
}
