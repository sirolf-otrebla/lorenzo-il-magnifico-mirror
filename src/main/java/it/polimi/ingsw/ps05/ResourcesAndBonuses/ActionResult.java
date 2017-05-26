package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

public interface ActionResult {

    public void applyResult(PlayerRelated playerR);

    public void setValue(Integer amount) throws NoSuchMethodException;

    public Integer getValue() throws NoSuchMethodException;

}
