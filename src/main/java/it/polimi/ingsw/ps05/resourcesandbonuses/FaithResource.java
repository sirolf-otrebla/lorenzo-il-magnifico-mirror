package it.polimi.ingsw.ps05.resourcesandbonuses;


import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.net.server.Game;

public class FaithResource implements Resource, ActionResult {

	//TODO : scegliere se usare due variabili separate o usarne una sola
	private Integer amount;
	private String id = "Fede";
	private Game game;

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
		playerR.getRelatedPlayer().addResource(this);
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		return game;
	}
}
