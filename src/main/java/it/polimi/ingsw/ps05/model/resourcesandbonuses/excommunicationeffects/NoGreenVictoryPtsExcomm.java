package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

import it.polimi.ingsw.ps05.model.effects.EffectType;

public class NoGreenVictoryPtsExcomm implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5138547132870452333L;
	
	Game game;

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
		familyMember.getRelatedPlayer().removeGreenCard();	
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		return malus;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

}
