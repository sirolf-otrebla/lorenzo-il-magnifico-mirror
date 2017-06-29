package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.net.Game;

public interface ActionResult extends Serializable {

    public void applyResult(PlayerRelated playerR);

    public void setValue(Integer amount) throws NoSuchMethodException;

    public Integer getValue() throws NoSuchMethodException;

    public void setGame(Game game);

    public Game getGame();


}
