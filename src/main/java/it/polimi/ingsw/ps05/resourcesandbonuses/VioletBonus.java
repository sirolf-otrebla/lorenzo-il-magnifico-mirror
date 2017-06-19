package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.VioletTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.Tower;
import it.polimi.ingsw.ps05.model.TowerTileInterface;
import it.polimi.ingsw.ps05.net.server.Game;

public class VioletBonus extends PermanentBonus{
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

	public VioletBonus(Integer value){
		this.value = value;
	}

	public VioletBonus() {

	}

	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		return value;
	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList()){
			if (t instanceof VioletTower){
				for (TowerTileInterface tile : t.getTiles()){
					tile.setDiceRequired(tile.getDiceRequired().getValue() - this.value);
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
		return "Bonus viola";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
