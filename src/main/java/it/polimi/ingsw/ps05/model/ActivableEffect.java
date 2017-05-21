package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class ActivableEffect implements Effect {
	
	ActivableEffectType type;
	Integer diceRequired;
	ArrayList<ArrayList<Resource>> resourcesRequired;
	ArrayList<ArrayList<ActionResult>> effects;

	// state modifiers represents if the player wants to use the effect or not.
	public static final int STATE_READY = 0;
	public static final int STATE_NOT_READY = 1;
	private int state = STATE_READY;
	private int choosenAlternative;
	public ActivableEffect(){
		
	}
	
	public ActivableEffect(ActivableEffectType type, Integer diceRequired,
			ArrayList<ArrayList<Resource>> resourcesRequired, ArrayList<ArrayList<ActionResult>> effects) {
		super();
		this.type = type;
		this.diceRequired = diceRequired;
		this.resourcesRequired = resourcesRequired;
		this.effects = effects;
	}





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

    @Override
    public void apply(PlayerRelated familyMember, int choosenAlternative) {

		if (state == this.STATE_READY){
			// algorithm:

            // 1) check dice
            // 1b) if dice not enough, remove servants
            // 1c) if not enough servants, rise exception
			// 2) remove resources
            // 2b) if not enough resources, rise exception
			// 3) apply
		}

    }

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
