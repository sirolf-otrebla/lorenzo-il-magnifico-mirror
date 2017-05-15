package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class VictoryResource implements Resource {
	private int amount;
	
	public VictoryResource(int amount){
		this.amount = amount;
	}
	
	public VictoryResource() {
		
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
