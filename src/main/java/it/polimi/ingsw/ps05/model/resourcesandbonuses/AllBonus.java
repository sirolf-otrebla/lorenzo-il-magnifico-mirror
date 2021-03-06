package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
/**
 * {@inheritDoc}
 *
 * this Action Result represent a die bonus on ALL types of action space.
 */
public class AllBonus extends PermanentBonus implements ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972328530854839238L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private transient Game game;
	private boolean hasListeners = false;

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
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
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
