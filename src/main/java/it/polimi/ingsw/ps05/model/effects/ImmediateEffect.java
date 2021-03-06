package it.polimi.ingsw.ps05.model.effects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;

public class ImmediateEffect implements SimpleEffect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3659808848678255420L;
	ArrayList<ActionResult> effectList = new ArrayList<ActionResult>();

	public ImmediateEffect() {
		super();
	}

	public void setEffectList(ArrayList<ActionResult> actResList){
	    this.effectList = actResList;
    }

	@Override
	public EffectType getEffectType() {
		return EffectType.IMMEDIATE;
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		return effectList;
	}

	@Override
	public void apply(PlayerRelated playerR) {
		for( ActionResult res : effectList )
			res.applyResult(playerR);
	}

	@Override
	public String toString(){

		return "Effetto immediato";
	}

}
