package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.server.net.Game;

public class StoneResourceCostBonus implements ActionResult, Resource {
	
	private Integer value;
	public static final  String ID = "Pietra Bonus";
	private Game game;
	
	public StoneResourceCostBonus(){
		
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
		return ID;
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
		return "Costo pietra";
	}

}
