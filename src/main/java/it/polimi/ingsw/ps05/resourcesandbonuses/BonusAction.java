package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

/**
 * Created by Alberto on 13/06/2017.
 */
public interface BonusAction {

    public void acceptListener(ResultTriggerVisitor visitor, PlayerRelated pl);
}
