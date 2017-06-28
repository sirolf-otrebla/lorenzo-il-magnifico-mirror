package it.polimi.ingsw.ps05.model.spaces;

import java.util.HashMap;

public class GreenTower extends Tower {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5863059715328768234L;

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
