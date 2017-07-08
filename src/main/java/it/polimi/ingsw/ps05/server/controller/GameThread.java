package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 08/07/2017.
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
