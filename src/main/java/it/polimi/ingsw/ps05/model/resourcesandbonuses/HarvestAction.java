package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.controller.Game;
import java.util.Iterator;
import java.util.Observable;

public class HarvestAction extends Observable implements ActionResult, BonusAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2399262282971411797L;
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	transient private Game game;

	public HarvestAction(Integer value){
		this.value = value;
	}

	public HarvestAction() {

	}

	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
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
			for (TowerTileInterface tile : t.getTiles().values()){
				TowerCard card = tile.getCard();
				card.addFalseResource();
			}
		}
		Iterator<ActionSpace> iterator = board.getActSpacesMap().values().iterator();
		while(iterator.hasNext()) iterator.next().addFalseResource();
		//notifica observer
		setChanged();
		notify();
	}
	
	public void resetResult(PlayerRelated playerR){
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList().values()){
				for (TowerTileInterface tile : t.getTiles().values()){
					TowerCard card = tile.getCard();
					card.removeFalseResource();
				}
		}
		for (ActionSpace a : board.getActSpacesMap().values()){
			a.removeFalseResource();
		}
	}


	@Override
	public Game getGame() {
		return game;
	}


	@Override
	public String toString(){
		return "Azione raccolto";
	}

}
