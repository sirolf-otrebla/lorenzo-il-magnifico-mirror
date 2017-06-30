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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3659476952391381973L;
	public Epoch epoch = new Epoch();
	public ColorEnumeration color;
	public String cardName = new String();
	public ArrayList<ArrayList<Resource>> requirements = new ArrayList<ArrayList<Resource>>();
	public ArrayList<Effect> effects = new ArrayList<Effect>();
	private static int counter = 0;
	private Integer referenceID;

	public TowerCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.requirements = requirements;
		this.effects = effects;
		this.referenceID = counter;
		counter++;
	}
	
	public TowerCard(Epoch epoch, ColorEnumeration color, String cardName, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.effects = effects;
		this.requirements = new ArrayList<ArrayList<Resource>>();
		this.referenceID = counter;
		counter++;
	}
	
	public TowerCard(){
		this.referenceID = counter;
		counter++;
	}
	
	public void addFalseResource(){
		for (ArrayList<Resource> or : requirements){
			or.add(new AlwaysUnFullFilledResource());
		}
	}
	
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
}
