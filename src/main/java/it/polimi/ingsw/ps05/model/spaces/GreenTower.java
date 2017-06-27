package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;
import java.util.HashMap;

public class GreenTower extends Tower {

	public GreenTower(HashMap<Integer, TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public GreenTower(){
		super();
	}

	@Override
	public void setTiles(HashMap<Integer, TowerTileInterface> tiles){
		super.setTiles(tiles);
	}
}
