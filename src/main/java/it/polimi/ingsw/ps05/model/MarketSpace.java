package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import java.util.Iterator;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;

public class MarketSpace extends ActionSpaceWithEffect {
    private ArrayList<Effect> effectList;
    private  int[] selectedEffects;
    
    @Override
    public ArrayList<Effect> getEffects() {
        return effectList;
    }

    @Override
    public void applyEffect() {
        //Player player = this.getOccupant().getRelatedPlayer();
        Iterator<Effect> it = this.getEffects().iterator();
        int index = 0;
        while (it.hasNext()){
            it.next().apply(this.getOccupant(), selectedEffects[index]);
            index++;
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
