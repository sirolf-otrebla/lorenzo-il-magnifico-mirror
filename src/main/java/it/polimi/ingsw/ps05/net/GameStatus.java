package it.polimi.ingsw.ps05.net;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 28/06/2017.
 */
public class GameStatus {

    private HashMap<Integer, Player> playerHashMap;
    private Board gameBoard;

    public HashMap<Integer, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public GameStatus(ArrayList<Player> playerList, Board gameBoard) {
        this.playerHashMap = new HashMap<>();
        for (Player p : playerList)
            this.playerHashMap.put(p.getPlayerID(), p);
        this.gameBoard = gameBoard;
    }
}
