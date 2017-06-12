package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;

public class PermanentEffect implements Effect {

	public static final int NO_ALTERNATIVES = 0; // permanent effetcs do not have alternatives.
	
	ArrayList<ArrayList<ActionResult>> effectsList;
	
	public void setEffectList(ArrayList<ArrayList<ActionResult>> effectsList){
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
	public void apply(PlayerRelated familyMember, int alternative) {
		for (ActionResult act:
				effectsList.get(alternative)) {
			familyMember.getRelatedPlayer().addPermanentEffectRes(act);
			act.applyResult(familyMember);
		}

	}

}
