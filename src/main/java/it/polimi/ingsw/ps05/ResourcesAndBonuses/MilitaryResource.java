package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class MilitaryResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private Integer value;
	
	public MilitaryResource(Integer amount){
		this.amount = amount;
	}
	
	public MilitaryResource() {
		
	}
	
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public Integer getAmount(){
		return this.amount;
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(Integer value){
		return this.value;
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
