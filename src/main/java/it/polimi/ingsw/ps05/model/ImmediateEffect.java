package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.resourcesandbonuses.*;

public class ImmediateEffect implements Effect {
	
	ArrayList<ActionResult> effectList = new ArrayList<ActionResult>();

	public ImmediateEffect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setEffectList(ArrayList<ActionResult> actResList){
	    this.effectList = actResList;
    }

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.IMMEDIATE;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		ArrayList<ArrayList<ActionResult>> list = new ArrayList<ArrayList<ActionResult>>();
		list.add(effectList);
		return list;
	}

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
		for( ActionResult res : getResultList().get(alternative) )
			res.applyResult(familyMember);
	}

}
