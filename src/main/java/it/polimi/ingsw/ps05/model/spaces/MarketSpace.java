package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;

import java.util.Iterator;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.Familiar;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.SimpleEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

public class MarketSpace extends SingleOccupantActionSpace {

	/**
	 *
	 *
	 */

	private static final long serialVersionUID = 3303459183874742240L;
	private ArrayList<Effect> effectList;
	private int[] selectedEffects = {};
	public static final int DEFAULT_INDEX = 0;
	private Integer marketSpaceTypeID;

	@Override
	public ArrayList<Effect> getEffects() {
		return effectList;
	}

	@Override
	public void applyEffect(Familiar pl) {
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
		super.setDiceRequirement(diceRequired);
		this.effectList = effectList;
	}


	public int[] getSelectedEffects() {
		return selectedEffects;
	}
	
	public Integer getMarketSpaceTypeID() {
		return marketSpaceTypeID;
	}
	
	public void setMarketSpaceTypeID(Integer marketSpaceTypeID){
		this.marketSpaceTypeID = marketSpaceTypeID;
	}

	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {
		// TODO Auto-generated method stub
		
	}
}
