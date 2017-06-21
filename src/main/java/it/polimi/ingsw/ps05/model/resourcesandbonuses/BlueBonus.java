package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.BlueTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.net.Game;

import java.util.Observable;

public class BlueBonus extends Observable implements ActionResult {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private  Game game;
	
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
		for (Tower t : board.getTowerList()){
			if (t instanceof BlueTower){
				for (TowerTileInterface tile : t.getTiles()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() - this.getValue());
				}
			}
		}
		setChanged();
		notify();
	}
	
	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList()){
			if (t instanceof BlueTower){
				for (TowerTileInterface tile : t.getTiles()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() + this.getValue());
				}
			}
		}
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
		addObserver(this.game.getGameFlowctrl().limitedBonusActListener);
	}

	@Override
	public Game getGame() {
		return game;
	}
	
	@Override
	public String toString(){
		return "Bonus blu";
	}
}
