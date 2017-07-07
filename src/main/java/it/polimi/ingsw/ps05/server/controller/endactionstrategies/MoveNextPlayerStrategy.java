package it.polimi.ingsw.ps05.server.controller.endactionstrategies;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameUpdateMessage;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;

/**
 * Created by Alberto on 07/07/2017.
 */
public class MoveNextPlayerStrategy implements EndActionStrategy {

    private Game game;
    public MoveNextPlayerStrategy(Game game){
        this.game = game;
    }

    @Override
    public void execute(EndActionStrategyContainer container) {
        game.getState().nextState();
        container.StrategyEnded();
       /* ArrayList<Player> playerArrayList = new ArrayList<>();
        for (PlayerClient c: game.getPlayerInGame().values()) {
            playerArrayList.add(c.getPlayer());
        }
        for (PlayerClient thisPlater: game.getPlayerInGame().values()) {
            GameStatus gameStatus = new GameStatus(playerArrayList, game.getBoard(),
                    thisPlater.getPlayer(), game.getActivePlayer().getPlayerID());
            thisPlater.sendMessage(new GameUpdateMessage(gameStatus));
        } */
        //TODO GAME STATUS
    }
}
