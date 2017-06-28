package it.polimi.ingsw.ps05.model.spaces;

import java.util.HashMap;

public class BlueTower extends Tower {

	public BlueTower(HashMap<Integer, TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public BlueTower(){
		super();
	}
	
	@Override
	public void setTiles(HashMap<Integer, TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
