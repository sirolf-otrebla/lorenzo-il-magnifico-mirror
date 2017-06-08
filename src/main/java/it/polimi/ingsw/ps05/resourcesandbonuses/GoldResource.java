package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class GoldResource implements Resource, ActionResult {
	//TODO: vedi faith resource
	private Integer amount;

	public GoldResource(Integer amount){
		this.amount = amount;
	}
	
	public GoldResource() {
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
	public void remove(Resource res) {

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) {

	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return false;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addGold(this);
	}

}
