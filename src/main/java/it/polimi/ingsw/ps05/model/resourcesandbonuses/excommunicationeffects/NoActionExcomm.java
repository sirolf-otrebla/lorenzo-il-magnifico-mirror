package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PermanentBonus;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.server.controller.startactionstrategies.DoNothingStrategy;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class NoActionExcomm extends PermanentBonus implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3208417758147281941L;

	transient Game game;

	@Override
	public EffectType getEffectType() {
		return EffectType.EXCOM;
	}
	
	private ArrayList<ActionResult> malus = null;

	@Override
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException{
		if (this.malus == null){
			this.malus = malus;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

	@Override
	public String toString(){
		return "Non esegui la prima azione del tuo turno";
	}
	
	@Override
	public void apply(PlayerRelated familyMember) {
		applyResult(familyMember);
		
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		return malus;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
		
	}

	@Override
	public void applyResult(PlayerRelated playerR) {
		game.getStartActionStrategyContainer().setChosenStrategy(new DoNothingStrategy(game, true));
		if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}
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
	public Game getGame() {
		return game;
	}

	@Override
	public void resetResult(PlayerRelated playerR) {
		game.getStartActionStrategyContainer().resetStrategy();
	}
}
