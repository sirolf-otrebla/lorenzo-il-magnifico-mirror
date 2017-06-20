package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.BlueTower;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.Tower;
import it.polimi.ingsw.ps05.model.TowerCard;
import it.polimi.ingsw.ps05.model.TowerTileInterface;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;

public class BlueAction extends Observable implements ActionResult, BonusAction {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	//Ex. value 5 è da Integerendersi come un azione con dado 5 sulla colonna blu

	private Game game;
	
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
		// TODO Auto-generated method stub
		//crea familiare ghost in player
		Familiar f = playerR.getRelatedPlayer().createGhostFamiliar(this.value);
		//modifica la board aggiungendo risorsa sempre falsa
		Board board = this.getGame().getBoard();
		for (Tower t : board.getTowerList()){
			if (!(t instanceof BlueTower)){
				for (TowerTileInterface tile : t.getTiles()){
					TowerCard card = tile.getCard();
					card.addFalseResource();
				}
			}
		}
		for (ActionSpace a : board.getActionSpace()){
			a.addFalseResource();
		}
		//notifica observer
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
		//TODO: this.addObserver(game.getGameFlowctrl().getBonusActListener());
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
}

