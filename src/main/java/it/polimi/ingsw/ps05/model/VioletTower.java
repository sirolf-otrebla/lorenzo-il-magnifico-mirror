package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class VioletTower extends Tower {

	public VioletTower(ArrayList<TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public VioletTower(){
		super();
	}
	
	@Override
	public void setTiles(ArrayList<TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
