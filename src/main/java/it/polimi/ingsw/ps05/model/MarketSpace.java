package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

/* TODO: write UMLs methods and attributes --Sirolfo */

import java.util.ArrayList;
import java.util.Iterator;

public class MarketSpace extends ActionSpaceWithEffect {
    private ArrayList<Effect> effectList;
    private  int[] selectedEffects;
    @Override
    public ArrayList<Effect> getEffects() {
        return effectList;
    }

    @Override
    public void applyEffect() {
        Player player = this.getOccupant().getRelatedPlayer();
        Iterator<Effect> it = this.getEffects().iterator();
        int index = 0;
        while (it.hasNext()){
            it.next().apply(this.getOccupant(), selectedEffects[index]);
            index++;
        }
    }

    public MarketSpace(ArrayList<Effect> marketEffectList ){
        this.effectList = marketEffectList;

    }

    public void setSelectedEffects(int[] selectedEffects) {
        this.selectedEffects = selectedEffects;
    }
	
	ArrayList<ActionResult> effectsOnPositioning;
	
	public MarketSpace(){
		super();
	}
	
	public MarketSpace(ArrayList<ActionResult> effectsOnPositioning){
		super();
		this.effectsOnPositioning = effectsOnPositioning;
	}
	
	public MarketSpace(Integer diceRequired, ArrayList<ActionResult> effectsOnPositioning){
		super();
		super.setDiceRequirement(diceRequired);
		this.effectsOnPositioning = effectsOnPositioning;
	}
	

    public int[] getSelectedEffects() {
        return selectedEffects;
    }
}
