package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.GoldResource;

/* See UML for thescription.
 * 
 * TODO: add UML methods and attributes. --Sirolfo
 */
public class Tower {

	public static final int TOWER_RENT_AMNT = 3;

	private GoldResource towerOccupiedGoldResource;
	boolean isOccupied;

	public Tower(){

		this.isOccupied = false;
		this.towerOccupiedGoldResource = new GoldResource();

		towerOccupiedGoldResource.setAmount(TOWER_RENT_AMNT);
	}

	public GoldResource getRentAmount(){
		return this.towerOccupiedGoldResource;
	}

}
