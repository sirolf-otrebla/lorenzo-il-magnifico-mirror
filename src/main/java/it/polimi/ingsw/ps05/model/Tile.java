package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.TowerOccupiedException;

import java.util.ArrayList;

/* represent the all card spaces within towers
 * 
 * further comments will be added.
 */
public class Tile extends ActionSpace implements TowerTileInterface {

    private Tower parentTower;
    private TowerCard card;

    @Override
    public ArrayList<Effect> getEffect() {
        return null;
    }

    @Override

    public boolean isOccupied() throws TowerOccupiedException{
        if (super.isOccupied()) return super.isOccupied();
        /* this exception is meant to be a way to communicate with higher level that the tower is already occupied, so that
        the player has to pay X coins;
         */
        if (parentTower.isOccupied) throw new TowerOccupiedException();
        else return super.isOccupied();
    }

    @Override

    public  ArrayList<Resource> getRequirements(){

        //TODO
        return null;
    }
}
