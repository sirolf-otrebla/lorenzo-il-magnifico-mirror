package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class WoodResource implements Resource, ActionResult {
	//TODO vedi faith
	private Integer amount;
	private String id = "Legno";
	
	public WoodResource(Integer amount){
		this.amount = amount;
	}
	
	public WoodResource() {
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
		playerR.getRelatedPlayer().addResource(this);
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
