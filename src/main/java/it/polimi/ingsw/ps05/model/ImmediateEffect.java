package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.*;

public class ImmediateEffect implements Effect {
	
	ArrayList<ArrayList<ActionResult>> effctsList;

	public ImmediateEffect() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setEffectList(ArrayList<ActionResult> effectsList){
		
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
	public void apply(Familiar familyMember, int alternative) {

	}

}
