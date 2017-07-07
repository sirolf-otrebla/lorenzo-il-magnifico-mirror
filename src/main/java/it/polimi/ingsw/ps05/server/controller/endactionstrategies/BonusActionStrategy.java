package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.gamemessages.BonusActionTriggerMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameUpdateMessage;
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
    private ColorEnumeration actionColor;
    private Familiar ghostFamiliar;

    public BonusActionStrategy(ColorEnumeration colorEnumeration, Familiar ghostFamiliar){
        this.actionColor = colorEnumeration;
        this.ghostFamiliar = ghostFamiliar;
    }

    @Override
    public void execute(EndActionStrategyContainer endActionStrategyContainer) {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        this.container = endActionStrategyContainer;
        this.gfc = container.getGame().getGameFlowctrl();
        this.game = container.getGame();
        for (PlayerClient client: game.getPlayerInGame().values())
            playerArrayList.add(client.getPlayer());

        for (PlayerClient cl: game.getPlayerInGame().values()) {
            GameStatus status = new GameStatus(playerArrayList, game.getBoard(),
                    cl.getPlayer(), game.getActivePlayer().getPlayerID());
            GameUpdateMessage bonusActionUpdateMessage = new GameUpdateMessage(status);
            BonusActionTriggerMessage msg = new BonusActionTriggerMessage
                    (this.actionColor,bonusActionUpdateMessage, this.ghostFamiliar);
            cl.sendMessage(bonusActionUpdateMessage);

        }
    }
}
