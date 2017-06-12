package it.polimi.ingsw.ps05.resourcesandbonuses;


import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;

public class FaithResource implements Resource, ActionResult {

	//TODO : scegliere se usare due variabili separate o usarne una sola
	private Integer amount;
	public static final String id = "Fede";

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
		setValue(this.getValue() - amount);
	}

	@Override
	public void remove(Resource res) {
		setValue(this.getValue() - res.getValue());
	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) {
		try {
			playerFamiliar.getRelatedPlayer().getResource(this.getId()).remove(this.getValue());
		} catch (NotEnoughResourcesException | IllegalMethodCallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		// TODO Auto-generated method stub
		return (playerFamiliar.getRelatedPlayer().getResource(this.getId()).getValue() >= this.getValue());
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addResource(this);
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
}