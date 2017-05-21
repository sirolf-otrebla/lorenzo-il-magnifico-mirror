package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class YellowCard extends TowerCard  {

	private static final int DEFAULT_EFFECT = 0;

	private boolean toBeActivated = true;

	private ArrayList<ActivableEffect> activableEffectList;
	private int[] selectedEffects;


	public int[] getSelectedEffects() {
		return selectedEffects;
	}

	public void setSelectedEffects(int[] selectedEffects) {
		this.selectedEffects = selectedEffects;
	}
	
	public YellowCard(Epoch epoch, Color color, String cardName,  ArrayList<ArrayList<Resource>> requirements,
			ArrayList<Effect> effects) {
		super(epoch, color, cardName, requirements, effects);

		for (Effect a: effects)
			if (a instanceof ActivableEffect) this.activableEffectList.add((ActivableEffect) a);
		selectedEffects = new int[activableEffectList.size()];
	}

	public void applyProductionEffects(Familiar familyMember){

			/*1st solution: Player selects what cards he want to use BEFORE
		 * calling action.run() class. references to cards he want to activate are stored
		  * in a special array/ list/ whatever attribute stored in Player class.
		  * this solution requires some kind of card related effect which
		 */

		// DICE CONTROL IS INSIDE EFFECT
		if(this.getToBeActivated()){
			// apply selected alternative for each effect which hase state = STATE_READY ( check ActivableEffect for details)
			for(int i = 0; activableEffectList.size() > i; i++)
				activableEffectList.get(i).apply(familyMember, selectedEffects[i]);
		}


	}

	public boolean getToBeActivated() {
		return toBeActivated;
	}

	public void setToBeActivated(boolean toBeActivated) {
		this.toBeActivated = toBeActivated;
	}
}
