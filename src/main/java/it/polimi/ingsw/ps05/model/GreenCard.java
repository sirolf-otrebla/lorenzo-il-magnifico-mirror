package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;

public class GreenCard extends TowerCard {
	
	public GreenCard(){
		super();
	}


	// selectedEffect has to be set before calling apply* methods;
	private static final int DEFAULT_EFFECT = 0;

	private boolean toBeActivated = true;
	private ArrayList<ActivableEffect> activableEffectList = new ArrayList<ActivableEffect>();

	// meant to be selected BEFORE using HARVEST
	private int[] selectedEffects;


	public GreenCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> effects){
		super(epoch, color, cardName, requirements, effects);

		// NOTE: THIS IS ONLY A PROVISIONAL WORKAROUND. NEED TO CHANGE JSON.
		for (Effect a: effects)
			if (a instanceof ActivableEffect) this.activableEffectList.add((ActivableEffect) a);
		selectedEffects = new int[activableEffectList.size()]; // ODIO GLI ARRAY ISTANZIATI A RUNTIME PERÒ È LA COSA PIU SVEGLIA IN QUESTO MOMENTO;

	}

	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Effect> getEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	public void applyHarvestableEffects(PlayerRelated familyMember){
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
		// TODO Auto-generated method stub
		return toBeActivated;
	}

	public void setToBeActivated(boolean toBeActivated) {
		this.toBeActivated = toBeActivated;
	}

	public GreenCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(epoch, color, cardName, effects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveToPlayer() {
		// TODO Auto-generated method stub
		
	}

}
