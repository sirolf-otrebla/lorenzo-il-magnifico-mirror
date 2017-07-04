package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.model.spaces.YellowTower;
import it.polimi.ingsw.ps05.server.controller.Game;

public class YellowBonus extends PermanentBonus{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8671801209944697132L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public YellowBonus(Integer value){
		this.value = value;
	}

	public YellowBonus() {

	}

	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (t instanceof YellowTower){
				for (TowerTileInterface tile : t.getTiles().values()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() - this.getValue());
				}
			}
		}
		setChanged();
		notify();
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
		return "Bonus giallo";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (t instanceof YellowTower){
				for (TowerTileInterface tile : t.getTiles().values()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() + this.getValue());
				}
			}
		}
	}
}
