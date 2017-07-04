package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.ProductionSpace;
import it.polimi.ingsw.ps05.server.controller.Game;

public class ProductionBonus extends PermanentBonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -239848225304423565L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public ProductionBonus(Integer value){
		this.value = value;
	}

	public ProductionBonus() {

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
		Board board = this.game.getBoard();
		for (ActionSpace a : board.getActSpacesMap().values()){
			if (a instanceof ProductionSpace){
				a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() - this.getValue()));
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
		return "Bonus produzione";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		Board board = this.game.getBoard();
		for (ActionSpace a : board.getActSpacesMap().values()){
			if (a instanceof ProductionSpace){
				a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() + this.getValue()));
			}
		}
	}
}
