package it.polimi.ingsw.ps05.resourcesandbonuses.ExcommunicationEffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;

import org.json.simple.JSONObject;

import it.polimi.ingsw.ps05.model.EffectType;

public class NoGreenVictoryPtsExcomm implements ExcommunicationEffect {

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void apply(Familiar familyMember, int alternative) {

	}

	@Override
    public void apply() {

    }

    @Override
	public void applyEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inizializeFromJson(JSONObject json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
		// TODO Auto-generated method stub
		
	}

}