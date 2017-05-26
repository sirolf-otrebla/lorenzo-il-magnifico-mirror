package it.polimi.ingsw.ps05.ResourcesAndBonuses;


import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class FaithResource implements Resource, ActionResult {

	//TODO : scegliere se usare due variabili separate o usarne una sola
	private Integer amount;

	public FaithResource(Integer amount) {
		this.amount = amount;

	}

	public FaithResource() {
		this.amount = 0;
	}

	public void setValue(Integer amount) {
		this.amount = amount;
	}

	public Integer getValue() {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addFaith(this);
	}
}
