package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.BlueTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

import java.util.Observable;

public class BlueBonus extends Observable implements ActionResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3795604773294229746L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private transient Game game;
	private boolean hasListeners = false;


	public BlueBonus(Integer value){
		this.value = value;
	}
	
	public BlueBonus() {
		
	}
	
	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (t instanceof BlueTower){
				for (TowerTileInterface tile : t.getTiles().values()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() - this.getValue());
				}
			}
		}
		if(hasListeners) {
			setChanged();
			notify();
		}
	}
	
	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (t instanceof BlueTower){
				for (TowerTileInterface tile : t.getTiles().values()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() + this.getValue());
				}
			}
		}
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
		return "Bonus blu";
	}

	@Override
	public void linkToGfcObservers() {
		addObserver(this.game.getGameFlowctrl().limitedBonusActListener);
		hasListeners = true;

	}
}
