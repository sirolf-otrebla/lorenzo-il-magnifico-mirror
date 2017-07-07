package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.net.message.GameUpdateMessage;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.GameFlowController;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 06/07/2017.
 */
public class BonusActionStrategy implements EndActionStrategy {
    private EndActionStrategyContainer container;
    private GameFlowController gfc;
    private Game game;

    public void BonusActionStrategy(EndActionStrategyContainer container){
        this.container = container;
        this.gfc = container.getGame().getGameFlowctrl();
        this.game = container.getGame();
    }

    @Override
    public void execute() {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        for (PlayerClient client: game.getPlayerInGame().values())
            playerArrayList.add(client.getPlayer());

        for (PlayerClient cl: game.getPlayerInGame().values()) {
            GameStatus status = new GameStatus(playerArrayList, game.getBoard(),
                    cl.getPlayer(), game.getActivePlayer().getPlayerID());
            GameUpdateMessage bonusActionUpdateMessage = new GameUpdateMessage(status);

        }
    }
}
