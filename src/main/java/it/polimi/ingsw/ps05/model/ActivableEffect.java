package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class ActivableEffect implements Effect {
	
	ActivableEffectType type;
	Integer diceRequired;
	ArrayList<ArrayList<Resource>> resourcesRequired;
	ArrayList<ArrayList<ActionResult>> effects;
	
	public ActivableEffect(){
		
	}
	
	public ActivableEffect(ActivableEffectType type, Integer diceRequired,
			ArrayList<ArrayList<Resource>> resourcesRequired, ArrayList<ArrayList<ActionResult>> effects) {
		super();
		this.type = type;
		this.diceRequired = diceRequired;
		this.resourcesRequired = resourcesRequired;
		this.effects = effects;
	}





	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<Effect>> getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

}
