package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.server.controller.Game;

public class MilitaryResourceToSub implements Resource, ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7829022042415624291L;
	private Integer amount;
	public static final String id = "Militari da Togliere";
	transient private Game game;

	public MilitaryResourceToSub(Integer amount){
		this.amount = amount;
	}
	
	public MilitaryResourceToSub() {
		
	}
	
	public void setValue(Integer amount){
		this.amount = amount;
	}
	
	public Integer getValue(){
		return this.amount;
	}

	@Override
	public void remove(Resource res) {

	}

	public void remove(Integer amount) {

	}

	@Override
	public void remove(int amount) throws NotEnoughResourcesException, IllegalMethodCallException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
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
		return "Militare da sub";
	}


}
