package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.net.Game;

public class AllBonus extends PermanentBonus implements ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972328530854839238L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private  Game game;

	public AllBonus(Integer value){
		this.value = value;
	}

	public AllBonus() {

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
			for (TowerTileInterface tile : t.getTiles().values()){
				tile.setDiceRequired(tile.getDiceRequired().getValue() - this.getValue());
			}
		}
		for (ActionSpace a : board.getActSpacesMap().values()){
			a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() - this.getValue()));
		}
		setChanged();
		notify();
	}
	
	@Override
	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			for (TowerTileInterface tile : t.getTiles().values()){
				tile.setDiceRequired(tile.getDiceRequired().getValue() + this.getValue());
			}
		}
		for (ActionSpace a : board.getActSpacesMap().values()){
			a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() + this.getValue()));
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
		return "Bonus globale";
	}

}
