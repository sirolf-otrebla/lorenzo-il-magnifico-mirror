package it.polimi.ingsw.ps05.model.spaces;

import java.util.HashMap;

public class VioletTower extends Tower {

	public VioletTower(HashMap<Integer, TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public VioletTower(){
		super();
	}
	
	@Override
	public void setTiles(HashMap<Integer, TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
