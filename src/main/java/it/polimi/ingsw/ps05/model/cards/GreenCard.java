package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class GreenCard extends TowerCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8383056887622761576L;


	public GreenCard(){
		super();
	}


	// selectedEffect has to be set before calling apply* methods;
	private static final int DEFAULT_EFFECT = 0;
	private int referenceId;
	private boolean toBeActivated = true;
	private ArrayList<ActivableEffect> activableEffectList = new ArrayList<ActivableEffect>();
	private ArrayList<Effect> immediateEffects = new ArrayList<>();

	// meant to be selected BEFORE using HARVEST
	private int[] selectedEffects;

	public GreenCard(int id, Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> effects){
		super(id, epoch, color, cardName, requirements, effects);

		// NOTE: THIS IS ONLY A PROVISIONAL WORKAROUND. NEED TO CHANGE JSON.
		for (Effect a: effects) {
			if (a instanceof ActivableEffect){
				this.activableEffectList.add((ActivableEffect) a);
			}
			else if (a instanceof ImmediateEffect)
				this.immediateEffects.add((ImmediateEffect) a);
			selectedEffects = new int[activableEffectList.size()]; // ODIO GLI ARRAY ISTANZIATI A RUNTIME PERÒ È LA COSA PIU SVEGLIA IN QUESTO MOMENTO;
		}
	}

	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {
		return requirements;
	}

	public void applyHarvestableEffects(PlayerRelated familyMember){
		/*1st solution: Player selects what cards he want to use BEFORE
		 * calling action.run() class. references to cards he want to activate are stored
		 * in a special array/ list/ whatever attribute stored in Player class.
		 * this solution requires some kind of card related effect which
		 */

		// DICE CONTROL IS INSIDE EFFEC
		// soluzione provvisoria per settare gli effetti, ad oggi le carte verdi non posseggono alternative
		if(this.getToBeActivated()){
			for (int i = 0; i < selectedEffects.length; i++) {
				selectedEffects[i] = 0;
			}
			// apply selected alternative for each effect which hase state = STATE_READY ( check ActivableEffect for details)
			for(int i = 0; activableEffectList.size() > i; i++)
				((AlternativeEffect)activableEffectList.get(i)).apply(familyMember, selectedEffects[i]);
		}
	}

	public boolean getToBeActivated() {
		return toBeActivated;
	}

	public void setToBeActivated(boolean toBeActivated) {
		this.toBeActivated = toBeActivated;
	}

	public GreenCard(int id, Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		super(id , epoch, color, cardName, effects);
	}

	@Override
	public void moveToPlayer(PlayerRelated player) {
		player.getRelatedPlayer().addGreenCard(this);
	}

	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		super.applyNonActivableEffects(player);
	}

	@Override
	public String toString(){
		return "Carta verde"; //CHI È IL GENIO?
	}

	public ArrayList<ActivableEffect> getActivableEffectList() {
		return activableEffectList;
	}
}
