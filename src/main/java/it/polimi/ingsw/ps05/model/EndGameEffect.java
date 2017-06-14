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
		return EffectType.END_GAME;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		ArrayList<ArrayList<ActionResult>> list = new ArrayList<ArrayList<ActionResult>>();
		list.add(effectsList);
		return list;
	}

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
		//Nothing to do here for now
		
	}

}
