package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;
import java.util.Observer;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PrivilegeBonus;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

public class LeaderCard implements Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = -294844779735811229L;
	private  ArrayList<ArrayList<Resource>> requirements;
	private  ArrayList<Effect> effects;
	private  String cardName;

	private final Integer referenceID;

	private static int count = 0;

	private Observer privilegeListener;

	private boolean active = false;
	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {

		return this.requirements;
	}

	@Override
	public ArrayList<Effect> getEffects() {
		return effects;
	}


	public boolean isActive() {
		return active;
	}

	public void applyNonActivableEffects(PlayerRelated player, int[] alternative) {
		for (int i = 0; i < effects.size(); i++)
		    ((SimpleEffect)effects.get(i)).apply(player);
	}

	public void discard(PlayerRelated playerRelated){
        ArrayList<LeaderCard> cardlist = playerRelated.getRelatedPlayer().getLeaderCardList();
	    for (LeaderCard card : cardlist){
	        if (card.equals(this)) cardlist.remove(this);
        }
        PrivilegeBonus priv = new PrivilegeBonus(1);
        //TODO: GESTIRE PRIVILEGI
    }
	
	@Override
	public EpochEnumeration getEpoch() {
		return EpochEnumeration.NO_EPOCH;
	}

    @Override
    public String getName() {
        return cardName;
    }


	public LeaderCard(){
    	super();
    	this.referenceID = LeaderCard.count; //TODO VEDI SE ESISTE UN METODO MIGLIORE
    	LeaderCard.count++;
	}

	public void setRequirements(ArrayList<ArrayList<Resource>> requirements) {
		this.requirements = requirements;
	}

	public void setEffects(ArrayList<Effect> effects) {
		this.effects = effects;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Override
	public void applyNonActivableEffects(PlayerRelated player) {
		for (int i = 0; i < effects.size(); i++)
		    ((SimpleEffect)effects.get(i)).apply(player);
	}


}
