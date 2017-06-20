package it.polimi.ingsw.ps05.model.exceptions;

import it.polimi.ingsw.ps05.model.spaces.Tower;

/**
 * Created by Alberto on 15/05/2017.
 */
public class TowerOccupiedException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tower towerOccupied;
    private boolean isActionSpaceOccupied;

    public Tower getTowerOccupied() {
        return towerOccupied;
    }

    public boolean isActionSpaceOccupied(){
        return this.isActionSpaceOccupied;
    }

    private void setTowerOccupied(Tower towerOccupied) {
        this.towerOccupied = towerOccupied;
    }

    public TowerOccupiedException(Tower tower, boolean isActionSpaceOccupied){
        setTowerOccupied(tower);
        this.isActionSpaceOccupied = isActionSpaceOccupied;
    }
}
