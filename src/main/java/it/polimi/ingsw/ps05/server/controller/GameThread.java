package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 08/07/2017.
 */

/**
 * this class is designed to host the construction and execution of a single game. in this way it
 * is possible to handle many Games without conflicts between a match and another
 */
public class GameThread implements Runnable {
    private boolean useCompleteRules = false;
    private boolean useCustomBonusTiles = false;
    private ArrayList<PlayerClient> connectedClientArrayList = new ArrayList<>();

    public GameThread(boolean useCompleteRules, boolean useCustomBonusTiles, ArrayList<PlayerClient> connectedClientArrayList) {
        this.useCompleteRules = useCompleteRules;
        this.useCustomBonusTiles = useCustomBonusTiles;
        for (PlayerClient c: connectedClientArrayList) {
            this.connectedClientArrayList.add(c);
        }

    }

    /** this is the main runnable procedure. it builds a new game using all the parameters setted by
     * the constructor, and executes the {@code game.start()} method
      */
    @Override
    public void run() {
        Game game = new Game(this.useCompleteRules, this.useCustomBonusTiles,
                Server.getInstance().getGamesNumber() +1, connectedClientArrayList);
        try {
            game.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
