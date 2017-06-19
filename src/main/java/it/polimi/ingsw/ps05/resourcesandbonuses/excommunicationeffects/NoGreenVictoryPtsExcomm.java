package it.polimi.ingsw.ps05.resourcesandbonuses.excommunicationeffects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

import org.json.simple.JSONObject;

import it.polimi.ingsw.ps05.model.EffectType;

public class NoGreenVictoryPtsExcomm implements ExcommunicationEffect {

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void apply(Familiar familyMember, int alternative) {

	}

	@Override
    public void apply() {

    }

    @Override
	public void applyEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inizializeFromJson(JSONObject json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(PlayerRelated familyMember, int alternative) {
		// TODO Auto-generated method stub
		
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
		return "Non prendi punti vittoria dalle carte verdi";
	}

}
