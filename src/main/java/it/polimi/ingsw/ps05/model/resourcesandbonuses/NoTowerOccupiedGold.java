package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoTowerOccupiedGold extends PermanentBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3630473764879782868L;
	transient Game game;

	@Override
	public void applyResult(PlayerRelated playerR) {
		for (Tower t : game.getBoard().getTowerList().values()){
			t.setRentAmount(0);
		}
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
	}

	@Override
	public void setValue(Integer amount) throws NoSuchMethodException {
		throw new NoSuchMethodException();

	}

	@Override
	public Integer getValue() throws NoSuchMethodException {
		throw new NoSuchMethodException();
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
		for (Tower t : game.getBoard().getTowerList().values()){
			t.setRentAmount(Tower.TOWER_RENT_AMNT);
		}
	}

}
