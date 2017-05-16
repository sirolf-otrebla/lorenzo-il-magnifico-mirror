package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class GoldResource implements Resource, ActionResult {
	//TODO: vedi faith resource
	private Integer amount;
	private Integer value;
	
	public GoldResource(Integer amount){
		this.amount = amount;
	}
	
	public GoldResource() {
		
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

	public void remove(Integer amount) throws NotEnoughResourcesException {

	}

	@Override
	public void remove(Resource res) {

	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub
		
	}

}
