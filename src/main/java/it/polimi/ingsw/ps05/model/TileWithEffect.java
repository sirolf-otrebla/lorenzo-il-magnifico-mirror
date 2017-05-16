package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

/*
 * this is a decorator for Tile class, designed to add tile-related effects \
 * 
 * further commments will be added.
 */

public class TileWithEffect extends ActionSpaceWithEffect implements TowerTileInterface {

	private Tile toBeDecorated;
	
	public TileWithEffect ( Tile tile){
		this.toBeDecorated = tile;
	}

	@Override
	public ArrayList<Effect> getEffect() {  //TODO
		return null;
	}
}
