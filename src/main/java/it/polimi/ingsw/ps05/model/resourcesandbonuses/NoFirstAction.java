package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import java.util.Iterator;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.BlueTower;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;

public class NoFirstAction extends PermanentBonus implements ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 282333671940817128L;
	transient Game game;

	@Override
	public void applyResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
				for (TowerTileInterface tile : t.getTiles().values()){
					TowerCard card = tile.getCard();
					card.addFalseResource();
				}
		}
		Iterator<ActionSpace> it = board.getActSpacesMap().values().iterator();
		while(it.hasNext()){
			it.next().addFalseResource();
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
		addObserver(this.game.getGameFlowctrl().limitedBonusActListener);
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
		for (Tower t : board.getTowerList().values()){
			if (!(t instanceof BlueTower)){
				for (TowerTileInterface tile : t.getTiles().values()){
					TowerCard card = tile.getCard();
					card.removeFalseResource();
				}
			}
		}
		Iterator<ActionSpace> it = board.getActSpacesMap().values().iterator();
		while(it.hasNext()){
			it.next().removeFalseResource();
		}
		//notifica observer
		setChanged();
		notify();
		
	}

}
