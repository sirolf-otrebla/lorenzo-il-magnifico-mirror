package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.resourcesandbonuses.GoldResource;

/* See UML for thescription.
 * 
 * TODO: add UML methods and attributes. --Sirolfo
 */
public abstract class Tower {

	public static final int TOWER_RENT_AMNT = 3;
	private GoldResource towerOccupiedGoldResource;
	boolean isOccupied;
	ArrayList<TowerTileInterface> tiles; //dove vanno messi i tile
	Deck deck;
	
	public Tower(){
		this.isOccupied = false;
		this.towerOccupiedGoldResource = new GoldResource();
		towerOccupiedGoldResource.setValue(TOWER_RENT_AMNT);
		//setParentListInTiles();
	}

	public Tower(ArrayList<TowerTileInterface> tiles){

		this.isOccupied = false;
		this.towerOccupiedGoldResource = new GoldResource();
		towerOccupiedGoldResource.setValue(TOWER_RENT_AMNT);
		this.tiles = tiles;
		System.out.println(tiles.size());
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
	
	public ArrayList<TowerTileInterface> getTiles(){
		return this.tiles;
	}
	
	public void setTiles(ArrayList<TowerTileInterface> tiles){
		this.tiles = tiles;
	}
	
	public void setDeck(Deck deck){
		this.deck = deck;
	}
	
	public void setCardInTile(Epoch epoch){
		for (TowerTileInterface o : tiles){
			o.setTowerCard(deck.getCard(epoch));
		}
	}
	
	

}
