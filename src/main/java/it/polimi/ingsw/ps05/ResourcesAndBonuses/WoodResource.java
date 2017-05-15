package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class WoodResource implements Resource {
	private int amount;
	
	public WoodResource(int amount){
		this.amount = amount;
	}
	
	public WoodResource() {
		
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException {

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException {

	}
}
