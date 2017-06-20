package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.ActionSpace;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.HarvestingSpace;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.Tower;
import it.polimi.ingsw.ps05.model.TowerCard;
import it.polimi.ingsw.ps05.model.TowerTileInterface;
import it.polimi.ingsw.ps05.model.YellowTower;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;

public class HarvestAction extends Observable implements ActionResult, BonusAction {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

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
			for (TowerTileInterface tile : t.getTiles()){
				TowerCard card = tile.getCard();
				card.addFalseResource();
			}
		}
		for (ActionSpace a : board.getActionSpace()){
			if (!(a instanceof HarvestingSpace)){
				a.addFalseResource();
			}
		}
		//notifica observer

	}

	@Override
	public void setGame(Game game) {
		this.game = game;
		//this.addObserver(game.getGameFlowctrl().getBonusActListener());
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
		return "Azione raccolto";
	}
}
