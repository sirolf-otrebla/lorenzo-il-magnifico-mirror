package it.polimi.ingsw.ps05.model.effects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;

public class EndGameEffect implements SimpleEffect {
	
	ArrayList<ActionResult> effectsList = new ArrayList<ActionResult>();
	
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
	public ArrayList<ActionResult> getResultList() {
		return effectsList;
	}

	@Override
	public void apply(PlayerRelated familyMember) {
		//Nothing to do here for now
		
	}

}
