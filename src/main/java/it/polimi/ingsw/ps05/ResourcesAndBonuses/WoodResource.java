package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class WoodResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private Integer value;
	
	public WoodResource(Integer amount){
		this.amount = amount;
	}
	
	public WoodResource() {
		
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
	
	public Integer getValue(){
		return this.value;
	}

	@Override
	public void remove(Integer amount) throws NotEnoughResourcesException {

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException {

	}
}
