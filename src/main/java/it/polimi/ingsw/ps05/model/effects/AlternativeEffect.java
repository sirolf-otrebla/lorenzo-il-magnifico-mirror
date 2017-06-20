package it.polimi.ingsw.ps05.model.effects;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;

import java.util.ArrayList;

/**
 * Created by Alberto on 19/06/2017.
 */
public interface AlternativeEffect extends Effect {

        public void apply(PlayerRelated playerRelated, int alternative);

        public ArrayList<ArrayList<ActionResult>> getResultList();


}
