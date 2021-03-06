package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.spaces.GreenTower;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.BonusActionStrategy;

import java.util.Iterator;
import java.util.Observable;

public class GreenAction extends Observable implements ActionResult, BonusAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7255786539292915638L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public GreenAction(Integer value){
		this.value = value;
	}

	public GreenAction() {

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
		//crea familiare ghost in player
		Familiar f = playerR.getRelatedPlayer().createGhostFamiliar(this.value);
		//modifica la board aggiungendo risorsa sempre falsa
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (!(t instanceof GreenTower)){
				for (TowerTileInterface tile : t.getTiles().values()){
					TowerCard card = tile.getCard();
					card.addFalseResource();
				}
			}
		}
		Iterator<ActionSpace> it = board.getActSpacesMap().values().iterator();
		while(it.hasNext()){
			it.next().addFalseResource();
		}
		this.game.getEndActionStrategyContainer().setChosenStrategy(
				new BonusActionStrategy(ColorEnumeration.Green, f));
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
		return "Azione verde";
	}



    public void resetResult(PlayerRelated playerR) {
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (!(t instanceof GreenTower)){
				for (TowerTileInterface tile : t.getTiles().values()){
					TowerCard card = tile.getCard();
					card.removeFalseResource();
				}
			}
		}
		for (ActionSpace a : board.getActSpacesMap().values()){
			a.removeFalseResource();
		}
	}
}
