package it.polimi.ingsw.ps05.model.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.spaces.VioletTower;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.model.cards.TowerCard;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;

public class VioletAction extends Observable implements ActionResult, BonusAction {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

	public VioletAction(Integer value){
		this.value = value;

	}

	public VioletAction() {

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
			if (!(t instanceof VioletTower)){
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
		//TODO this.addObserver(game.getGameFlowctrl().getBonusActListener());
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public String toString(){
		return "Azione viola";
	}

	@Override
	public void acceptListener(ResultTriggerVisitor visitor, PlayerRelated pl) {
		visitor.visit(this, pl );
	}
}
