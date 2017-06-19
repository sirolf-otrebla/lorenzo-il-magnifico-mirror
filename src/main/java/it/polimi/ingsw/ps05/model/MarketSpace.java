package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import java.util.Iterator;

import it.polimi.ingsw.ps05.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.resourcesandbonuses.Dice;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;

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
        	((SimpleEffect)it.next()).apply(this.getOccupant());
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
		try {
		    super.setDiceRequirement(diceRequired);
            this.effectList = effectList;
		} catch (RepeatedAssignmentException e){
		    //todo
        }
	}
	

    public int[] getSelectedEffects() {
        return selectedEffects;
    }

}
