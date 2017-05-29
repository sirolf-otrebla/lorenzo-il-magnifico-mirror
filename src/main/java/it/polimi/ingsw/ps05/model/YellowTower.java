package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class YellowTower extends Tower {

	public YellowTower(ArrayList<TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public YellowTower(){
		super();
	}
	
	@Override
	public void setTiles(ArrayList<TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
