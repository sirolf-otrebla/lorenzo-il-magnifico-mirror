package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;

public class GreenTower extends Tower {

	public GreenTower(ArrayList<TowerTileInterface> tiles) {
		super(tiles);
	}
	
	public GreenTower(){
		super();
	}

	@Override
	public void setTiles(ArrayList<TowerTileInterface> tiles){
		super.setTiles(tiles);
	}
}
