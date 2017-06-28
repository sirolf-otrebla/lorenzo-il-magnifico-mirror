package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.net.Game;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class NoActionExcomm implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3208417758147281941L;
	
	Game game;

	@Override
	public EffectType getEffectType() {
		return EffectType.EXCOM;
	}
	
	private ArrayList<ActionResult> malus = null;

	@Override
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException{
		// TODO Auto-generated method stub
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
