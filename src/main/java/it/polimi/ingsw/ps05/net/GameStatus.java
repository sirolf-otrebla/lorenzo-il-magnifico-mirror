package it.polimi.ingsw.ps05.net;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 28/06/2017.
 */
public class GameStatus implements Serializable{


    public static final int NO_PLAYER_ACTIVE = -999;
    private HashMap<Integer, Player> playerHashMap;
    private Board gameBoard;
    private Player thisPlayer;
    private Integer activePlayerId;

    public Player getThisPlayer() {
        return thisPlayer;
    }

    public HashMap<Integer, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public GameStatus(ArrayList<Player> playerList, Board gameBoard, Player thisPlayer, Integer activePlayerId) {
        this.playerHashMap = new HashMap<>();
        for (Player p : playerList)
            this.playerHashMap.put(p.getPlayerID(), p);
        this.gameBoard = gameBoard;
        this.activePlayerId = activePlayerId;

        this.thisPlayer = thisPlayer;
    }

    public Integer getActivePlayerId() {
        return activePlayerId;
    }
}
