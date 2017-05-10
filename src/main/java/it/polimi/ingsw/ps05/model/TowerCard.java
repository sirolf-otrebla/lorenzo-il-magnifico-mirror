package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

public class TowerCard implements Card {
	
	public Epoch epoch = new Epoch();
	public Color color = new Color();
	public String cardName = new String();
	public ArrayList<ArrayList<Resource>> requirements = new ArrayList<ArrayList<Resource>>();
	public ArrayList<ArrayList<Resource>> effects = new ArrayList<ArrayList<Resource>>();

	public TowerCard(Epoch epoch, Color color, String cardName, ArrayList<ArrayList<Resource>> requirements, ArrayList<ArrayList<Resource>> effects) {
		super();
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.requirements = requirements;
		this.effects = effects;
	}
	
	public TowerCard(Epoch epoch, Color color, String cardName, ArrayList<ArrayList<Resource>> effects) {
		super();
		this.epoch = epoch;
		this.color = color;
		this.cardName = cardName;
		this.effects = effects;
	}

	@Override
	public ArrayList<ArrayList<Resource>> getRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<Resource>> getEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Epoch getEpoch() {
		// TODO Auto-generated method stub
		return null;
	}

}
