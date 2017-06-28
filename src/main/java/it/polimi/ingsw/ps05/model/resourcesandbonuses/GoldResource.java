package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.server.net.Game;

public class GoldResource implements Resource, ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5857907131060281475L;
	//TODO: vedi faith resource
	private Integer amount;
	public static final String id = "Oro";
	private Game game;


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
		setValue(this.getValue() - amount);
	}

	@Override
	public void remove(Resource res) {
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
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return (playerFamiliar.getRelatedPlayer().getResource(this.getID()).getValue() >= this.getValue());
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		playerR.getRelatedPlayer().addResource(this);
	}

	@Override
	public String getID() {
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

	@Override
	public String toString(){
		return "Oro";
	}
}
