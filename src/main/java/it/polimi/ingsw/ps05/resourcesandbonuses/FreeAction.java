package it.polimi.ingsw.ps05.resourcesandbonuses;

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

public class FreeAction extends Observable implements ActionResult, BonusAction {
	private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;

	public FreeAction(Integer value){
		this.value = value;
	}

	public FreeAction() {

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
		//niente da modificare, azione free
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
		return "Azione libera";
	}

}
