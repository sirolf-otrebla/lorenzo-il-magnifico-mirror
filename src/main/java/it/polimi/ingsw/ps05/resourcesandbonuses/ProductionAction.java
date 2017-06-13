package it.polimi.ingsw.ps05.resourcesandbonuses;

import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.net.server.Game;
import it.polimi.ingsw.ps05.scrap.ResultTriggerVisitor;

import java.util.Observable;

public class ProductionAction extends Observable implements ActionResult, BonusAction {
private Integer value; //con value si Integerende il valore del bonus conferito dalla carta
	private Game game;
	
	public ProductionAction(Integer value){
		this.value = value;
		this.addObserver(game.getGameFlowctrl().getBonusActListener());
	}
	
	public ProductionAction() {
		this.addObserver(game.getGameFlowctrl().getBonusActListener());
	}
	
	public void setValue(Integer value){
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return  value;

	}

	public Integer getAmount(){
		return this.value;
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		// TODO Auto-generated method stub
		
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
	public void acceptListener(ResultTriggerVisitor visitor, PlayerRelated pl) {
		visitor.visit(this, pl );
	}
}
