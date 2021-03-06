package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.server.controller.Game;

public class WoodResource implements Resource, ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = -220328697689674078L;
	private Integer amount;
	public static final String id = "Legno";
	transient private Game game;
	
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
		if (this.getValue() - amount < 0) {
			throw new NotEnoughResourcesException();
		}
		setValue(this.getValue() - amount);
	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		if (!res.getID().equals(this.getID())) throw new IllegalMethodCallException();
		if (this.getValue() - res.getValue() < 0) throw new NotEnoughResourcesException();
		setValue(this.getValue() - res.getValue());
	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) {
		try {
			playerFamiliar.getRelatedPlayer().getResource(this.getID()).remove(this.getValue());
		} catch (NotEnoughResourcesException | IllegalMethodCallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addResource(this);
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return (playerFamiliar.getRelatedPlayer().getResource(this.getID()).getValue() >= this.getValue());
	}

	@Override
	public String getID() {
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
	
	@Override
	public String toString(){
		return new String("Legno ");
	}


}
