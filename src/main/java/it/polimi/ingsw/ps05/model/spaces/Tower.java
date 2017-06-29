package it.polimi.ingsw.ps05.model.spaces;

import java.io.Serializable;
import java.util.HashMap;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;
import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.cards.Deck;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;

/* See UML for thescription.
 * 
 */
public abstract class Tower implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656424293015641983L;
	public static final int TOWER_RENT_AMNT = 3;
	private GoldResource towerOccupiedGoldResource;
	private boolean occupied;
	HashMap<Integer, TowerTileInterface> tiles; //dove vanno messi i tile
	Deck deck;
	private ColorEnumeration color = ColorEnumeration.NOT_INITIALIZED;
	
	public Tower(){
		this.occupied = false;
		this.towerOccupiedGoldResource = new GoldResource();
		towerOccupiedGoldResource.setValue(TOWER_RENT_AMNT);
		//setParentListInTiles();
	}

	public Tower(HashMap<Integer, TowerTileInterface> tiles){

		this.occupied = false;
		this.towerOccupiedGoldResource = new GoldResource();
		towerOccupiedGoldResource.setValue(TOWER_RENT_AMNT);
		this.tiles = tiles;
		System.out.println(tiles.size());
		setParentListInTiles();
	}
	
	private void setParentListInTiles(){
		for (TowerTileInterface o : tiles.values()){
			o.setParentTower(this);
		}
	}
	
	public boolean isOccupied(){
		return occupied;
	}

	public GoldResource getRentAmount(){
		return this.towerOccupiedGoldResource;
	}
	
	public HashMap<Integer, TowerTileInterface> getTiles(){
		return this.tiles;
	}
	
	public void setTiles(HashMap<Integer, TowerTileInterface> tiles){
		this.tiles = tiles;
	}
	
	public void setDeck(Deck deck){
		this.deck = deck;
		this.color = this.deck.getCard(new Epoch(EpochEnumeration.FIRST)).color;
	}
	
	public ColorEnumeration getColor(){
		return this.color;
	}
	
	public void setCardInTile(Epoch epoch){
		for (TowerTileInterface o : tiles.values()){
			o.setTowerCard(deck.getCard(epoch));
		}
	}
	
	

}
