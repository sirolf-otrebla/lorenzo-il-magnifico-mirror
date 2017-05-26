package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

public class ImmediateEffect implements Effect {
	
	ArrayList<ArrayList<ActionResult>> effectList;

	public ImmediateEffect() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void addEffectList(ArrayList<ActionResult> effectsList){
		
	}

	public void setEffectList(ArrayList<ArrayList<ActionResult>> actResList){
	    this.effectList = actResList;
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
		for( ActionResult res : getResultList().get(alternative) )
			res.applyResult(familyMember);
	}

}
