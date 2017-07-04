package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoOccupiedSpace extends PermanentBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3661417399312197123L;
	ArrayList<ActionSpace> modified = new ArrayList<>();
	transient Game game;

	@Override
	public void applyResult(PlayerRelated playerR) {
		for (ActionSpace a : game.getBoard().getActSpacesMap().values()){
			if (a.isOccupied()){
				a.temporarySetFree();
				modified.add(a);
			}
		}
		
		for (Tower t : game.getBoard().getTowerList().values()) {
			for (TowerTileInterface tile : t.getTiles().values()){
				if (tile.isOccupied()){
					((ActionSpace)tile).temporarySetFree();
					modified.add((ActionSpace)tile);
				}
			}
		}

	}

	@Override
	public void setValue(Integer amount) throws NoSuchMethodException {
		throw new NoSuchMethodException();

	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		throw new NoSuchMethodException();
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
	public void resetResult(PlayerRelated playerR) {
		for (ActionSpace a : modified){
			a.temporarySetOccupied();
		}
		modified = new ArrayList<>();
	}

}
