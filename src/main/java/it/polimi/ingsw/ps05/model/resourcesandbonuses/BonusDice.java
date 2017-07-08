package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.server.controller.Game;

public class BonusDice extends PermanentBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4761876138993965465L;
	Integer value;
	ColorEnumeration color;
	transient Game  game;
	boolean toAdd = true;
	Integer oldValue;
	
	public BonusDice(Integer value, ColorEnumeration color, boolean toAdd){
		this.value = value;
		this.color = color;
		this.toAdd = toAdd;
	}
	
	

	public ColorEnumeration getColor() {
		return color;
	}



	public boolean isToAdd() {
		return toAdd;
	}



	@Override
	public void applyResult(PlayerRelated playerR) {
		if (toAdd){
		playerR.getRelatedPlayer().getFamilyMember(color).setDice(
				new Dice(color, playerR.getRelatedPlayer().getFamilyMember(color).getRelatedDice().getValue() + value));
		}else{
			oldValue = playerR.getRelatedPlayer().getFamilyMember(color).getRelatedDice().getValue();
			playerR.getRelatedPlayer().getFamilyMember(color).setDice(
					new Dice(color,value));
		}
			
	}

	@Override
	public void setValue(Integer amount) throws NoSuchMethodException {
		this.value = amount;
	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		return value;
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
	public void resetResult(PlayerRelated playerR) {
		if (toAdd){
		playerR.getRelatedPlayer().getFamilyMember(color).setDice(
				new Dice(color, playerR.getRelatedPlayer().getFamilyMember(color).getRelatedDice().getValue() - value));
		} else {
			playerR.getRelatedPlayer().getFamilyMember(color).setDice(
					new Dice(color, oldValue));
		}
		
	}

}
