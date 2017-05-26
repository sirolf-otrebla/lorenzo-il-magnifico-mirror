package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class VictoryResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	
	public VictoryResource(Integer amount){
		this.amount = amount;
	}
	
	public VictoryResource() {
		this.amount = 0;
	}
	
	public void setValue(Integer amount){
		this.amount = amount;
	}
	
	public Integer getValue(){
		return this.amount;
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
		playerR.getRelatedPlayer().addVictory(this);
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		// TODO Auto-generated method stub
		return false;
	}
}
