package it.polimi.ingsw.ps05.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.EffectType;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

public class ReduceVictoryPtsExcomm implements ExcommunicationEffect {

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inizializeFromJson(JSONObject json) {
		// TODO Auto-generated method stub
		
	}
	
	private ArrayList<ActionResult> malus = null;
	
	private ArrayList<ActionResult> toCheck = null;

	@Override
	public void setMalus(ArrayList<ActionResult> malus) throws RepeatedAssignmentException{
		// TODO Auto-generated method stub
		if (this.malus == null){
			this.malus = malus;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	
	public void setToCheck(ArrayList<ActionResult> toCheck) throws RepeatedAssignmentException{
		// TODO Auto-generated method stub
		if (this.toCheck == null){
			this.toCheck = toCheck;
		} else {
			throw new RepeatedAssignmentException();
		}
	}
	@Override
	public String toString(){
		return null;
	}
	
	@Override
	public void apply(PlayerRelated familyMember) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ActionResult> getResultList() {
		// TODO Auto-generated method stub
		return malus;
	}
	

}
