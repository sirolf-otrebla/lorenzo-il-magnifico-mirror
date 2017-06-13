package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import java.util.Iterator;

import it.polimi.ingsw.ps05.resourcesandbonuses.Dice;

public class MarketSpace extends ActionSpaceWithEffect {
    private ArrayList<Effect> effectList;
    private int[] selectedEffects = {};
    public static final int DEFAULT_INDEX = 0;
    
    @Override
    public ArrayList<Effect> getEffects() {
        return effectList;
    }

    @Override
    public void applyEffect() {
        //Player player = this.getOccupant().getRelatedPlayer();
        Iterator<Effect> it = this.getEffects().iterator();
        while (it.hasNext()){
            it.next().apply(this.getOccupant(), MarketSpace.DEFAULT_INDEX/*selectedEffects[MarketSpace.DEFAULT_INDEX]*/);
        }
    }

    public MarketSpace(ArrayList<Effect> marketEffectList ){
    	super();
        this.effectList = marketEffectList;
    }

    public void setSelectedEffects(int[] selectedEffects) {
        this.selectedEffects = selectedEffects;
    }
	
	public MarketSpace(){
		super();
	}
	
	public MarketSpace(Dice diceRequired, ArrayList<Effect> effectList){
		super();
		super.setDiceRequirement(diceRequired);
		this.effectList = effectList;
	}
	

    public int[] getSelectedEffects() {
        return selectedEffects;
    }
}
