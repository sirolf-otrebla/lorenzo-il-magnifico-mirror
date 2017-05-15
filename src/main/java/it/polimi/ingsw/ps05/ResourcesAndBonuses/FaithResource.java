package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class FaithResource implements Resource {
	
	private int amount;
	
	public FaithResource(int amount){
		this.amount = amount;
	}
	
	public FaithResource() {
		
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
	public void remove(Resource res) {

	}
}
