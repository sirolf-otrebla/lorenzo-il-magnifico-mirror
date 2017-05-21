package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public abstract class TowerCard implements Card {
	
	public Epoch epoch = new Epoch();
	public Color color = new Color();
	public String cardName = new String();
	public ArrayList<ArrayList<Resource>> requirements;
	public ArrayList<Effect> effects;

	public TowerCard(Epoch epoch, Color color, String cardName, ArrayList<ArrayList<Resource>> requirements, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.requirements = requirements;
		this.effects = effects;
	}
	
	public TowerCard(Epoch epoch, Color color, String cardName, ArrayList<Effect> effects) {
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.effects = effects;
	}
	
	public TowerCard(){
		
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
		return null;
	}

	public void applyNonActivableEffects(PlayerRelated player) {
		// TODO Auto-generated method stub
		
	}

	public abstract void moveToPlayer();
}
