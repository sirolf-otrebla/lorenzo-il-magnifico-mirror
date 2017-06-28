package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.net.Game;

import java.io.Serializable;
import java.util.ArrayList;

public interface ExcommunicationEffect extends SimpleEffect, Serializable {

	
	// bool checkAction(Action a); this checks if an action is possible or not based on ExcommunicationEffect
	// It is a good solution? --Sirolfo
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException;
	
	public void setGame(Game game);
	
}
