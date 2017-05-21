package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.PrivilegeBonus;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class LeaderCard implements Card {

	private ArrayList<ArrayList<Resource>> requirements;
	private ArrayList<Effect> effects;
	private String cardName;

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

	@Override
	public void applyNonActivableEffects(PlayerRelated player, int[] alternative) {
		for (int i = 0; i < effects.size(); i++)
		    effects.get(i).apply(player, alternative[i]);
	}

	public void discard(PlayerRelated playerRelated){
        ArrayList<LeaderCard> cardlist = playerRelated.getRelatedPlayer().getLeaderCardList()
	    for (LeaderCard card : cardlist){
	        if (card.equals(this)) cardlist.remove(this);
        }
        PrivilegeBonus priv = new PrivilegeBonus();
        priv.setValue(1);
        //TODO: GESTIRE PRIVILEGI
    }
	@Override
	public EpochEnumeration getEpoch() {
		// TODO Auto-generated method stub
		return EpochEnumeration.NO_EPOCH;
	}

    @Override
    public String getName() {
        return cardName;
    }

    public LeaderCard(String Name, ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> eff){
		this.cardName = Name;
		this.requirements = requirements;
		this.effects = eff;
	}


}
