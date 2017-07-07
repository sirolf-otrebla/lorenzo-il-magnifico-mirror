package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.BlueTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.BonusActionStrategy;

import java.util.Iterator;
import java.util.Observable;

public class BlueAction extends Observable implements ActionResult, BonusAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3121318190477832098L;

	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	//Ex. value 5 è da Integerendersi come un azione con dado 5 sulla colonna blu

	private transient Game game;

	public BlueAction(Integer value){
		this.value = value;

	}

	public BlueAction() {

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
		//crea familiare ghost in player
		Familiar f = playerR.getRelatedPlayer().createGhostFamiliar(this.value);
		//modifica la board aggiungendo risorsa sempre falsa
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (!(t instanceof BlueTower)){
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
				new BonusActionStrategy(ColorEnumeration.Blue, f));
	}

	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
			if (!(t instanceof BlueTower)){
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

	@Override
	public void setGame(Game game) {
		this.game = game;
		addObserver(this.game.getGameFlowctrl().bonusActListener);
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public void acceptListener(ResultTriggerVisitor visitor, PlayerRelated pl) {
		visitor.visit(this, pl );
	}


	@Override
	public String toString(){
		return "Azione blu";
	}

	@Override
	public void linkToActionListeners() {
		addObserver(this.game.getGameFlowctrl().bonusActListener);

	}

    @Override
    public void notifyToActionListeners() {

    }
}

