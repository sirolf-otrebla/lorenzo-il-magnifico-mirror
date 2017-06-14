package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;

public class VioletCard extends TowerCard  {
	
	public VioletCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}
	
	public VioletCard(Epoch epoch, ColorEnumeration color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<Effect> effects) {
		super(epoch, color, cardName, requirements, effects);
	}
	
	public VioletCard(){
		super();
	}

	@Override
	public void moveToPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "VioletCard [epoch=" + epoch.toString() + ", color=" + color + ", cardName=" + cardName + ", requirements="
				+ requirements.size() + ", effects=" + effects.size() + "]";
	}
	
	

}
