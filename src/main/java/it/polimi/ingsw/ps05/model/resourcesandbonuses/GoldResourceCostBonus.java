package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.server.controller.Game;

public class GoldResourceCostBonus implements ActionResult, Resource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5951593920487375809L;

	private Integer value;

	public static final String id = "Oro Bonus";
	transient private  Game game;

	public GoldResourceCostBonus(){
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) throws NotEnoughResourcesException, DiceTooLowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		
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
		return "Costo oro";
	}


}
