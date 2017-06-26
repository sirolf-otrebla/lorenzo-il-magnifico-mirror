package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.spaces.GreenTower;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Iterator;
import java.util.Observable;

public class GreenAction extends Observable implements ActionResult, BonusAction {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

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
		for (Tower t : board.getTowerList()){
			if (!(t instanceof GreenTower)){
				for (TowerTileInterface tile : t.getTiles()){
					TowerCard card = tile.getCard();
					card.addFalseResource();
				}
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
		return "Azione verde";
	}


	public void resetResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList()){
			if (!(t instanceof GreenTower)){
				for (TowerTileInterface tile : t.getTiles()){
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
