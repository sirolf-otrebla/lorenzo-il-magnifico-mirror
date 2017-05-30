package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class BonusTile {
	private Player relatedPlayer;
	private ArrayList<Effect> effectList;
	private BonusTileType type;
	
	public BonusTile() {
		
	}
	
	public BonusTile(ArrayList<Effect> effectList, BonusTileType type){
		this.effectList = effectList;
		this.type = type;
	}

	public Player getRelatedPlayer() {
		return relatedPlayer;
	}

	public ArrayList<Effect> getEffectList() {
		return effectList;
	}

	public BonusTileType getType() {
		return type;
	}

	public void setRelatedPlayer(Player relatedPlayer) {
		this.relatedPlayer = relatedPlayer;
	}

	public void setEffectList(ArrayList<Effect> effectList) {
		this.effectList = effectList;
	}

	public void setType(BonusTileType type) {
		this.type = type;
	}
	
	
}
