package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class StoneResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private Integer value;
	
	public StoneResource(Integer amount){
		this.amount = amount;
	}
	
	public StoneResource() {
		
	}
	
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException {

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) {

	}
}
