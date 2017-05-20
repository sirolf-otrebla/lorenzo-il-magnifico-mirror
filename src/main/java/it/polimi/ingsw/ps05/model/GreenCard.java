package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class GreenCard extends TowerCard {
	
	public GreenCard(){
		super();
	}

	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Effect> getEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public GreenCard(Epoch epoch, Color color, String cardName, ArrayList<Effect> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}
	
	public GreenCard(Epoch epoch, Color color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<Effect> effects) {
		super(epoch, color, cardName, requirements, effects);
	}

}
