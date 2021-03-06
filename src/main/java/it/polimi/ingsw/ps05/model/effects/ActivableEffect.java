package it.polimi.ingsw.ps05.model.effects;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class ActivableEffect implements AlternativeEffect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7676584572036711660L;
	ActivableEffectType type;
	Integer diceRequired = 0;
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


	public ActivableEffectType getActivableEffectType(){
		return type;
	}
	
	public Integer getDiceRequired(){
		return diceRequired;
	}


	@Override
	public EffectType getEffectType() {
		return EffectType.ACTIVABLE;
	}

	@Override
	public ArrayList<ArrayList<ActionResult>> getResultList() {
		return effects;
	}
	
	public ArrayList<ArrayList<Resource>> getResourceRequired(){
		return resourcesRequired;
	}

    @Override
    public void apply(PlayerRelated familyMember, int choosenAlternative) {

		if (state == ActivableEffect.STATE_READY){
			boolean go = true;
			if(resourcesRequired.size() > 0) {
				go = false;
				for (Resource r : getResourceRequired().get(choosenAlternative)) {
					go = r.hasEnoughResources(((Familiar) familyMember));
					if (!go) break;
				}
			}
			if (go){
				for (ActionResult r : getResultList().get(choosenAlternative)){
					r.applyResult(familyMember);
				}
			}
			
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
	
	@Override
	public String toString(){
		return getEffectType().toString();
	}
}
