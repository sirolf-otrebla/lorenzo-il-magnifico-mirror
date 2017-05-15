package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class GoldResource implements Resource {
	private int amount;
	
	public GoldResource(int amount){
		this.amount = amount;
	}
	
	public GoldResource() {
		
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
