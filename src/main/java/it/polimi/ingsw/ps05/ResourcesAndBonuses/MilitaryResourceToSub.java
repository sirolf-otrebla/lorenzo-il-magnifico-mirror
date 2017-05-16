package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class MilitaryResourceToSub implements Resource, ActionResult {

	private Integer amount;
	
	public MilitaryResourceToSub(Integer amount){
		this.amount = amount;
	}
	
	public MilitaryResourceToSub() {
		
	}
	
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public Integer getAmount(){
		return this.amount;
	}

	@Override
	public void remove(Resource res) {

	}

	public void remove(Integer amount) {

	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub
		
	}
}
