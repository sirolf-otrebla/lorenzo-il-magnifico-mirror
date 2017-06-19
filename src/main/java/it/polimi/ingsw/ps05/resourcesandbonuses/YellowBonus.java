package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.Tower;
import it.polimi.ingsw.ps05.model.TowerTileInterface;
import it.polimi.ingsw.ps05.model.YellowTower;
import it.polimi.ingsw.ps05.net.server.Game;

public class YellowBonus extends PermanentBonus{
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

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
		for (Tower t : board.getTowerList()){
			if (t instanceof YellowTower){
				for (TowerTileInterface tile : t.getTiles()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() - this.getValue());
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
		return "Bonus giallo";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
