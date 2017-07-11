package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PermanentBonus;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

import it.polimi.ingsw.ps05.model.effects.EffectType;

public class NoGreenVictoryPtsExcomm extends PermanentBonus implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5138547132870452333L;

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
		return "Non prendi punti vittoria dalle carte verdi";
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
		playerR.getRelatedPlayer().removeGreenCard();
		/*if (!playerR.getRelatedPlayer().getPermanentBonusList().contains(this)){
			playerR.getRelatedPlayer().getPermanentBonusList().add(this);
		}*/
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
		
	}

}
