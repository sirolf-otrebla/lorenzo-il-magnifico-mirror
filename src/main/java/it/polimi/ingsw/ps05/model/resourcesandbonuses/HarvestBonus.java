package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;

public class HarvestBonus extends PermanentBonus{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4353497814018798307L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public HarvestBonus(Integer value){
		this.value = value;
	}

	public HarvestBonus() {

	}

	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return  value;
	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.game.getBoard();
		for (ActionSpace a : board.getActSpacesMap().values()){
			if (a instanceof HarvestingSpace){
				a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() - this.getValue()));
			}
		}
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
	}

	@Override
	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (ActionSpace a : board.getActSpacesMap().values()){
			if (a instanceof HarvestingSpace){
				a.setDiceRequirement(new Dice(ColorEnumeration.Any, a.getDiceRequirement().getValue() + this.getValue()));
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
		return "Bonus raccolto";
	}

}
