package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 07/07/2017.
 */
public class BonusHarvestStrategy implements EndActionStrategy {
    @Override
    public void execute(EndActionStrategyContainer container) {
        Game game = container.getGame();
        ArrayList<Player> playerArrayList = new ArrayList<>();
        for (PlayerClient client: game.getPlayerInGame().values())
            playerArrayList.add(client.getPlayer());
        for (PlayerClient cl: game.getPlayerInGame().values()) {
            GameStatus status = new GameStatus(playerArrayList, game.getBoard(),
                    cl.getPlayer(), game.getActivePlayer().getPlayerID());
        }

    }
}
