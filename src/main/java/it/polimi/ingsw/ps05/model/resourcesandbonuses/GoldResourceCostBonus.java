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

public class GoldResourceCostBonus extends PermanentBonus implements ActionResult, Resource {
	
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
						if (r.getID().equals(GoldResource.id)){
							r.setValue(r.getValue() + this.getValue());
						}
					}
				}
			}
		}
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
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

	@Override
	public void resetResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				for (ArrayList<Resource> e : tile.getCard().getRequirements()){
					for (Resource r : e){
						if (r.getID().equals(GoldResource.id)){
							r.setValue(r.getValue() - this.getValue());
						}
					}
				}
			}
		}
	}


}
