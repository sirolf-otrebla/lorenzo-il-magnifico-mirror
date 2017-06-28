package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import org.json.simple.JSONObject;

import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;

import java.io.Serializable;
import java.util.ArrayList;

public interface ExcommunicationEffect extends SimpleEffect, Serializable {

	//TODO: create classes that implements this interface. how can we represent excommunicationeffects?
	
	// bool checkAction(Action a); this checks if an action is possible or not based on ExcommunicationEffect
	// It is a good solution? --Sirolfo
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException;
	
	public void applyEffect();
	
	public void inizializeFromJson(JSONObject json);

    default void apply() {

    }
}
