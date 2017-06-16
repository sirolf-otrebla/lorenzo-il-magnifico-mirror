package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;

public class PermanentEffect implements Effect {

	public static final int NO_ALTERNATIVES = 0; // permanent effetcs do not have alternatives.
	
	ArrayList<ActionResult> effectsList = new ArrayList<ActionResult>();
	
	public void setEffectList(ArrayList<ActionResult> effectsList){
		this.effectsList = effectsList;
	}

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.PERMANENT;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		ArrayList<ArrayList<ActionResult>> list = new ArrayList<ArrayList<ActionResult>>();
		list.add(effectsList);
		return list;
	}

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
			familyMember.getRelatedPlayer().addPermanentEffectRes(effectsList.get(alternative));
			effectsList.get(alternative).applyResult(familyMember);
	}

}
