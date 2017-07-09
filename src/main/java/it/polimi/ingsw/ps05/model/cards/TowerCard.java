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
	 * @param requirements these are the card's requirements, first List represent an OR plane, the inner list represent
	 *                      an AND plane: then this parameter can be seen as a SOP logic formula: for example, if the requirements
	 *                      are: (Resource1 + Resource2) OR (Resource3 + Resource4), they can be represented in this list as a 2-sized
	 *                      list which is composed by two other list, the firs containing Resource1 and Resource2, and the
	 *                      second one containing Resource3 and Resource4
	 *                      @see Resource
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
	 * The false resource is a resource that no one can have and is used to inhibit someone
	 * from taking the card (that happens when you have a special effect that allows you to do an action
	 * only in a section of the board
	 */
	public void addFalseResource(){
		for (ArrayList<Resource> or : requirements){
			or.add(new AlwaysUnFullFilledResource());
		}
	}
	/**
	 * Removes the false resource described before
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

	/** this method applies the card's effects to a player using {@link PlayerRelated} interface, which is now implemented by
	 *  both {@link it.polimi.ingsw.ps05.model.Player} and FamilyMember Classes.
	 *
	 * @param player	this is the player that will gain the card's effects
	 */
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

	/** this method moves the card from is Tower Tile to the player using {@link PlayerRelated} interface, which is now implemented by
	 *  both {@link it.polimi.ingsw.ps05.model.Player} and FamilyMember Classes. It is implemented by its subclasses
	 * 	@see BlueCard
	 * 	@see YellowCard
	 * 	@see GreenCard
	 * 	@see VioletCard
	 *
	 * @param player	this is the player  where the card will be moved
	 */
	public abstract void moveToPlayer(PlayerRelated player);

	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
}
