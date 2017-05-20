package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.TowerOccupiedException;

import java.util.ArrayList;

/* represent the all card spaces within towers
 * 
 * further comments will be added.
 */
public class Tile extends ActionSpace implements TowerTileInterface {

    private Tower<?> parentTower;
    private TowerCard card;
    private Integer diceRequired; //integer o dado??
    
    public Tile(){
    	
    }
    
    public Tile(TowerCard card, Integer diceRequired, Tower<?> parentTower){
    	
    }
    
    public void setDiceRequired(Integer diceRequired){
    	this.diceRequired = diceRequired;
    }
    public Integer getDiceRequired(){
    	return diceRequired;
    }
    
    @Override
    public void setParentTower(Tower<?> parentTower){
    	this.parentTower = parentTower;
    }
    
    @Override
    public void setTowerCard(TowerCard card){
    	this.card = card;
    }

    @Override
    public ArrayList<Effect> getEffects() {

        return this.card.getEffects();
    }

    @Override
    public boolean isOccupied() throws TowerOccupiedException{
        if (super.isOccupied()) return super.isOccupied();
        /* this exception is meant to be a way to communicate with higher level that the tower is already occupied, so that
        the player has to pay X coins;
         */
        if (parentTower.isOccupied) throw new TowerOccupiedException(this.parentTower, super.isOccupied());
        else return super.isOccupied();
    }

    @Override

    public  ArrayList<Resource> getRequirements(){

        //TODO
        return null;
    }
}
