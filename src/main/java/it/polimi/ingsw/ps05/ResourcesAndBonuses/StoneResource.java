package it.polimi.ingsw.ps05.ResourcesAndBonuses;


import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class StoneResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private Integer value;
	
	public StoneResource(Integer amount){
		this.amount = amount;
		this.value = 0;
	}
	
	public StoneResource() {
		this.amount = 0;
		this.value = 0;
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

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addStone(this);
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		// TODO Auto-generated method stub
		return false;
	}
}
