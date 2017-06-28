package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;
import java.util.HashMap;

public class YellowTower extends Tower {

	public YellowTower(HashMap<Integer, TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public YellowTower(){
		super();
	}
	
	@Override
	public void setTiles(HashMap<Integer, TowerTileInterface> tiles){
		super.setTiles(tiles);
	}

}
