package it.polimi.ingsw.ps05.model.spaces;

import java.util.HashMap;

public class YellowTower extends Tower {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
