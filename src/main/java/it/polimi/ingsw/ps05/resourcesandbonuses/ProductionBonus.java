package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.server.Game;

public class ProductionBonus extends PermanentBonus {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

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
		// TODO Auto-generated method stub

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
		return "Bonus produzione";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
