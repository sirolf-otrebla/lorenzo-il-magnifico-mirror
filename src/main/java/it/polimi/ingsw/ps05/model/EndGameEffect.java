package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;

public class EndGameEffect implements Effect {
	
	ArrayList<ActionResult> effectsList;
	
	public EndGameEffect(){
		
	}
	
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

    public void apply() {

    }

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
		// TODO Auto-generated method stub
		
	}

}
