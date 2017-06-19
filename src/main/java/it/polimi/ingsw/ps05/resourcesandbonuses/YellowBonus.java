package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
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
		return "Bonus giallo";
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		//todo
	}
}
