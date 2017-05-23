package it.polimi.ingsw.ps05.ResourcesAndBonuses.ExcommunicationEffects;

import org.json.simple.JSONObject;

import it.polimi.ingsw.ps05.model.Effect;

public interface ExcommunicationEffect extends Effect {

	//TODO: create classes that implements this interface. how can we represent ExcommunicationEffects?
	
	// bool checkAction(Action a); this checks if an action is possible or not based on ExcommunicationEffect
	// It is a good solution? --Sirolfo
	
	public void applyEffect();
	
	public void inizializeFromJson(JSONObject json);

    default void apply() {

    }
}
