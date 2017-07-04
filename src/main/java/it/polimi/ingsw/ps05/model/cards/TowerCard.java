package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.effects.PermanentEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.AlwaysUnFullFilledResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PermanentBonus;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public abstract class TowerCard implements Card {
	
	private static final long serialVersionUID = 3659476952391381973L;
	public Epoch epoch = new Epoch();
	public ColorEnumeration color;
	public String cardName = new String();
	public ArrayList<ArrayList<Resource>> requirements = new ArrayList<ArrayList<Resource>>();
	public ArrayList<Effect> effects = new ArrayList<Effect>();
	private Integer referenceID;

	/**
	 * TowerCard is a card designed to stay in tower
	 * @param referenceID is the unique ID of the card
	 * @param epoch is the epoch in which the card will appear in the board
	 * @param color is the color of the card
	 * @param cardName is the unique name of the card
	 * @param requirements are the resource that you must have to take the card (there could be more than 
	 * one way to pay)
	 * @param effects are the action that the card allows you to do
	 */
	public TowerCard(int referenceID, Epoch epoch, ColorEnumeration color, String cardName,
					 ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.requirements = requirements;
		this.effects = effects;
		this.referenceID = referenceID;
	}
	/**
	 * TowerCard is a card designed to stay in tower
	 * @param referenceID is the unique ID of the card
	 * @param epoch is the epoch in which the card will appear in the board
	 * @param color is the color of the card
	 * @param cardName is the unique name of the card
	 * @param effects are the action that the card allows you to do
	 */
	public TowerCard(int referenceID, Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.effects = effects;
		this.requirements = new ArrayList<ArrayList<Resource>>();
		this.referenceID = referenceID;
	}
	
	public TowerCard(){
	}
	/**
	 * The false resource is a resource that non one can have and is used to inhibit someone
	 * from taking the card (that happens when you have a special effect that allows you to do an action
	 * only in a section of the board
	 */
	public void addFalseResource(){
		for (ArrayList<Resource> or : requirements){
			or.add(new AlwaysUnFullFilledResource());
		}
	}
	/**
	 * Remove the resource added before
	 */
	public void removeFalseResource(){
		for (ArrayList<Resource> or : requirements){
			for (Resource r : or){
				if (r.getID().equals(AlwaysUnFullFilledResource.id)){
					or.remove(r);
				}
			}
		}
	}

	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {
		return this.requirements;
	}

	@Override
	public ArrayList<Effect>getEffects() {

		return this.effects;
	}

	@Override
	public String getName() {
		return this.cardName;
	}

	@Override
	public EpochEnumeration getEpoch() { // che cazzo???
		return this.epoch.getID();
	}

	public void applyNonActivableEffects(PlayerRelated player) {
		//ciclare negli effetti, prendere quelli immediati ed attivarli, prendere quelli di fine partita
		//e non farci niente, verranno conteggiati alla fine
		//prendere gli attivabili e non farci niente, prendere i permanenti e aggiungerli alla lista
		for (Effect e : effects){
			if (e instanceof ImmediateEffect){
				((ImmediateEffect) e).apply(player);
			} else if (e instanceof PermanentEffect){
				for (ActionResult b : ((PermanentEffect)e).getResultList()){
					player.getRelatedPlayer().addPermanentEffectRes((PermanentBonus)b);
				}
			}
		}
	}

	public Integer getReferenceID() {
		return referenceID;
	}

	public abstract void moveToPlayer(PlayerRelated player);

	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
}
