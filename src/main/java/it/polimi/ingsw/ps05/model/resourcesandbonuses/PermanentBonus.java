package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;

/**
 * Created by Alberto on 14/06/2017.
 */
public abstract class PermanentBonus implements ActionResult {

    public abstract  void resetResult(PlayerRelated playerR);
}
