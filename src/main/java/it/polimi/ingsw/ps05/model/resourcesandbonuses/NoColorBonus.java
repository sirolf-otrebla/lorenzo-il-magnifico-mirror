package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoColorBonus extends PermanentBonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8105323045051472500L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public NoColorBonus(Integer value){
		this.value = value;
	}

	public NoColorBonus() {

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
		for (Familiar f : playerR.getRelatedPlayer().getFamilyList()){
			if (f.getColor().equals(ColorEnumeration.Any)) {
				f.setDice(new Dice(ColorEnumeration.Any, f.getRelatedDice().getValue() + this.value));
			}
		}
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
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
		return "Bonus neutro";
	}

    @Override
	public void resetResult(PlayerRelated playerR) {
		for (Familiar f : playerR.getRelatedPlayer().getFamilyList()){
			if (f.getColor().equals(ColorEnumeration.Any)) {
				f.setDice(new Dice(ColorEnumeration.Any, f.getRelatedDice().getValue() - this.value));
			}
		}
	}
}
