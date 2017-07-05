package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.Turn;

import java.util.ArrayList;

/**
 * Created by Alberto on 19/06/2017.
 */
public class RoundController {

    public static final int  ACTIONS_PER_TURN = 4;

    private Turn turn;
    private ArrayList<Player> playerOrder;
    private Game game;
    private ArrayList<Round> roundList;

    public RoundController(Turn turn, Game game){
        this.turn = turn;
        this.playerOrder = turn.getPlayerOrder();
        this.roundList = new  ArrayList<Round>();
        this.game = game;
    }

    public void executeTurn() throws InterruptedException{
        for (int i = 0; i < Game.FAM_DIM ; i++){
            Round round = new Round(playerOrder, game);
            round.executeRound();
            roundList.add(round);
        }
    }

    public Turn getTurn() {
        return turn;
    }
    public ArrayList<Round> getRoundList() {
        return roundList;
    }
}
