package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.GoldResource;

/* See UML for thescription.
 * 
 * TODO: add UML methods and attributes. --Sirolfo
 */
public class Tower <T extends TowerCard> {

	public static final int TOWER_RENT_AMNT = 3;

	private T cardColorClass;
	private GoldResource towerOccupiedGoldResource;
	boolean isOccupied;
	ArrayList<TowerTileInterface> tiles; //dove vanno messi i tile

	public Tower(T colorCardClass, ArrayList<TowerTileInterface> tiles){

		this.isOccupied = false;
		this.towerOccupiedGoldResource = new GoldResource();
		towerOccupiedGoldResource.setAmount(TOWER_RENT_AMNT);
		this.cardColorClass = colorCardClass;
		this.tiles = tiles;
		setParentListInTiles();
	}
	
	private void setParentListInTiles(){
		for (TowerTileInterface o : tiles){
			o.setParentTower(this);
		}
	}

	public GoldResource getRentAmount(){
		return this.towerOccupiedGoldResource;
	}

	public T getCardColorClass() {
		return cardColorClass;
	}

	public void setCardColorClass(T cardColorClass) {
		this.cardColorClass = cardColorClass;
	}
	
	

}
