package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

public class PermanentEffect implements Effect {
	
	ArrayList<ActionResult> effectsList;
	
	public void setEffectList(ArrayList<ActionResult> effectsList){
		this.effectsList = effectsList;
	}

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

    @Override
    public void apply() {

    }

}
