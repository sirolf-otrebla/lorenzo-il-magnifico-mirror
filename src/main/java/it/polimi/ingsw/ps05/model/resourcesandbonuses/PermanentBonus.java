package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.Observable;

import it.polimi.ingsw.ps05.model.PlayerRelated;

/**
 * Created by Alberto on 14/06/2017.
 */
public abstract class PermanentBonus extends Observable implements ActionResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2643486938713305364L;

	public abstract  void resetResult(PlayerRelated playerR);

    @Override
    public void notifyToActionListeners() {

    }
}
