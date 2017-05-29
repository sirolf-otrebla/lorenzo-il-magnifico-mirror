package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

public class BlueTower extends Tower{

	public BlueTower(ArrayList<TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public BlueTower(){
		super();
	}
	
	@Override
	public void setTiles(ArrayList<TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
