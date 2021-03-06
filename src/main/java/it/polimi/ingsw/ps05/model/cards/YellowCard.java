package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.ActivableEffect;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class YellowCard extends TowerCard {


	/**
	 * 
	 */


	private static final long serialVersionUID = -4527055209026270239L;

	private static final int DEFAULT_EFFECT = 0;

	private boolean toBeActivated = true;

	private ArrayList<ActivableEffect> activableEffectList = new ArrayList<ActivableEffect>();
	private int selectedEffects;


	
	public YellowCard(int id, Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(id, epoch, color, cardName, effects);
		for (Effect a: effects)
			if (a instanceof ActivableEffect) this.activableEffectList.add((ActivableEffect) a);
		selectedEffects = 0;
	}

	public YellowCard(int id, Epoch epoch, ColorEnumeration color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<Effect> effects) {
		super(id, epoch, color, cardName, requirements, effects);

		for (Effect a: effects)
			if (a instanceof ActivableEffect) this.activableEffectList.add((ActivableEffect) a);
		selectedEffects = 0;
	}
	
	public int getSelectedEffects() {
		return selectedEffects;
	}

	public void setSelectedEffects(int selectedEffects) {
		this.selectedEffects = selectedEffects;
	}

	public void applyProductionEffects(PlayerRelated familyMember){

		/*1st solution: Player selects what cards he want to use BEFORE
		 * calling action.run() class. references to cards he want to activate are stored
		 * in a special array/ list/ whatever attribute stored in Player class.
		 * this solution requires some kind of card related effect which
		 */

		// DICE CONTROL IS INSIDE EFFECT
		if(this.getToBeActivated()){
			// apply selected alternative for each effect which hase state = STATE_READY ( check ActivableEffect for details)
			for(int i = 0; activableEffectList.size() > i; i++)
				activableEffectList.get(i).apply(familyMember, selectedEffects);
		}


	}

	public boolean getToBeActivated() {
		return toBeActivated;
	}

	public YellowCard(){
		super();
	}

	public void setToBeActivated(boolean toBeActivated) {
		this.toBeActivated = toBeActivated;
	}

	@Override
	public void moveToPlayer(PlayerRelated player) {
		player.getRelatedPlayer().addYellowCard(this);

	}

	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		super.applyNonActivableEffects(player);
	}
	
	@Override
	public String toString(){
		return "Carta gialla";
	}

	public ArrayList<ActivableEffect> getActivableEffectList() {
		return activableEffectList;
	}
}
