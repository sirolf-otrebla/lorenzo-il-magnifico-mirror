package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.server.Game;

public interface ActionResult {

    public void applyResult(PlayerRelated playerR);

    public void setValue(Integer amount) throws NoSuchMethodException;

    public Integer getValue() throws NoSuchMethodException;

    public void setGame(Game game);

    public Game getGame();


}
