package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.Turn;
import it.polimi.ingsw.ps05.net.server.Game;

import java.util.ArrayList;

/**
 * Created by Alberto on 19/06/2017.
 */
public class RoundController {

    public static final int  ACTIONS_PER_TURN = 4;

    private Turn turn;
    private ArrayList<Player> playerOrder;
    private Game game;

    public RoundController(Turn turn, Game game){
        this.turn = turn;
        turn.getPlayerOrder();
    }



}
