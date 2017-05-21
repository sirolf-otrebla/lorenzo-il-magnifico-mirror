package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard implements Card {

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

	@Override
	public void applyImmediateEffect(Object player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EpochEnumeration getEpoch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ExcommunicationCard(){
		// TODO Check constructor
	}

}
