package it.polimi.ingsw.ps05.ResourcesAndBonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class GoldResource implements Resource, ActionResult {
	//TODO: vedi faith resource
	private Integer amount;
	private Integer value;
	
	public GoldResource(Integer amount){
		this.amount = amount;
		this.value = 0;
	}
	
	public GoldResource() {
		this.amount = 0;
		this.value = 0;
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
