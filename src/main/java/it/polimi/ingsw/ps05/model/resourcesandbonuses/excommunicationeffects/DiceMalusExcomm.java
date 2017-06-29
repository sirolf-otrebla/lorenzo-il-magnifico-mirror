package it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.server.controller.Game;
import it.polimi.ingsw.ps05.model.effects.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class DiceMalusExcomm implements ExcommunicationEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8647656084663944173L;
	
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
		String forRes = new String();
		for (ActionResult r : malus){
			try {
				forRes = forRes + r.toString() + " " + r.getValue() + "\n";
			} catch (NoSuchMethodException e) {
				//non si entra mai qui
				e.printStackTrace();
			}
		}
		
		return "Hai un " + forRes;
	}
	
	@Override
	public void apply(PlayerRelated familyMember) {
		for (ActionResult r : malus){
			r.applyResult(familyMember);
		}
		
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
