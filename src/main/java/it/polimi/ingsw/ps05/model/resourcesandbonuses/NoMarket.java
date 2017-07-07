package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.Iterator;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.MarketSpace;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoMarket extends PermanentBonus implements ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229611496109790398L;

	transient Game game;

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		Iterator<ActionSpace> it = board.getActSpacesMap().values().iterator();
		while(it.hasNext()){
			if (it.next() instanceof MarketSpace){
				it.next().addFalseResource();
			}
		}
		//notifica observer
		setChanged();
		notify();

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
		return this.game;
	}

	@Override
	public void linkToActionListeners() {
		addObserver(this.game.getGameFlowctrl().limitedBonusActListener);
	}

    @Override
    public void notifyToActionListeners() {

    }

    @Override
	public void resetResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		Iterator<ActionSpace> it = board.getActSpacesMap().values().iterator();
		while(it.hasNext()){
			if (it.next() instanceof MarketSpace){
				it.next().removeFalseResource();
			}
		}
		
	}

}
