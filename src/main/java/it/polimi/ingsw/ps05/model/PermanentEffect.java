package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.resourcesandbonuses.PermanentBonus;

public class PermanentEffect implements SimpleEffect {


	
	ArrayList<ActionResult> effectsList;
	
	public void setEffectList(ArrayList<ActionResult> effectsList){
		this.effectsList = effectsList;
	}

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.PERMANENT;
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		// TODO Auto-generated method stub

		return  effectsList;
}

	public void apply(PlayerRelated familyMember ) {
		for (ActionResult act:
				effectsList) {
			familyMember.getRelatedPlayer().addPermanentEffectRes((PermanentBonus) act);
			act.applyResult(familyMember);

		} 
	}

}
