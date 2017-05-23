package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class BlueCard extends TowerCard {
	
	public BlueCard(Epoch epoch, Color color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<Effect> effects) {
		super(epoch, color, cardName, requirements, effects);
	}

	public BlueCard(Epoch epoch, Color color, String cardName, ArrayList<Effect> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}
	
	public BlueCard(){
		super();
	}

	@Override
	public void moveToPlayer() {
		// TODO Auto-generated method stub
		
	}
	
	
}
