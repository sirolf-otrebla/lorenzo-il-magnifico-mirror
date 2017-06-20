package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.GreenTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.net.Game;

public class GreenBonus extends PermanentBonus{
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private  Game game;

	public GreenBonus(Integer value){
		this.value = value;
	}

	public GreenBonus() {

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
			if (t instanceof GreenTower){
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
		return "Bonus verde";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
