package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.DiceTooLowException;
import it.polimi.ingsw.ps05.model.exceptions.IllegalMethodCallException;
import it.polimi.ingsw.ps05.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

public class StoneResourceCostBonus extends PermanentBonus implements ActionResult, Resource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 375568512526772790L;
	private Integer value;
	public static final  String ID = "Pietra Bonus";
	transient private Game game;
	
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
		throw new IllegalMethodCallException();

	}

	@Override
	public void remove(Resource res) throws NotEnoughResourcesException, IllegalMethodCallException {
		throw new IllegalMethodCallException();

	}

	@Override
	public void removeFromPlayer(Familiar playerFamiliar) throws NotEnoughResourcesException, DiceTooLowException {
		throw new NotEnoughResourcesException();	
	}

	@Override
	public boolean hasEnoughResources(Familiar playerFamiliar) {
		return false;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				for (ArrayList<Resource> e : tile.getCard().getRequirements()){
					for (Resource r : e){
						if (r.getID().equals(StoneResource.id)){
							r.setValue(r.getValue() + this.getValue());
						}
					}
				}
			}
		}
	}

	@Override
	public String getID() {
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

	@Override
	public void resetResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				for (ArrayList<Resource> e : tile.getCard().getRequirements()){
					for (Resource r : e){
						if (r.getID().equals(StoneResource.id)){
							r.setValue(r.getValue() - this.getValue());
						}
					}
				}
			}
		}
		
	}

}
