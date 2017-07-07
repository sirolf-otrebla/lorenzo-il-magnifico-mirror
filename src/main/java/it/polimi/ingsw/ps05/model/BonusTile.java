package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.effects.Effect;

import java.io.Serializable;
import java.util.ArrayList;

/** this class represents the personal Bonus Tile which is used during Harvest OR Production Actions
 *
 */
public class BonusTile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2084387351064961858L;
	private Player relatedPlayer;
	private ArrayList<Effect> effectList = new ArrayList<Effect>();
	private BonusTileType type;
	
	public BonusTile() {
		
	}

	/**
	 *
	 * @param effectList must be the list of effects triggered when an Harvesting or Production action is executed
	 * @param type represents if a Bonus tile is the DEFAULT bonus tile or a custom Bonus Tile
	 */
	public BonusTile(ArrayList<Effect> effectList, BonusTileType type){
		this.effectList = effectList;
		this.type = type;
	}

	/**
	 *
	 * @return returns the player that possesses this bonus tile
	 */
	public Player getRelatedPlayer() {
		return relatedPlayer;
	}

	/**
	 *
	 * @return returns the object's effect list
	 */
	public ArrayList<Effect> getEffectList() {
		return effectList;
	}

	/**
	 *
	 * @return returns the object's  type, which can be DEFAULT or CUSTOM
	 */
	public BonusTileType getType() {
		return type;
	}

	/**this method sets the player which possesses this object.
	 *
	 * @param relatedPlayer is the player related to this object
	 */
	public void setRelatedPlayer(Player relatedPlayer) {
		this.relatedPlayer = relatedPlayer;
	}

	/** this is a runtime method used by our classloader to load from Json files the tile configuration
	 *  it is not designed to be used at game time
	 * @param effectList	sets the object's effect list
	 */
	public void setEffectList(ArrayList<Effect> effectList) {
		this.effectList = effectList;
	}

	/** this is a runtime method used by our classloader to load from Json files the tile configuration
	 *  it is not designed to be used at game time
	 * @param type	sets the object's type, which can be DEFAULT or CUSTOM
	 */
	public void setType(BonusTileType type) {
		this.type = type;
	}
	
	
}
