package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.GoldResource;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;

import java.util.ArrayList;

/* represent the all card spaces within towers
 * 
 * further comments will be added.
 */
public class Tile extends ActionSpace implements TowerTileInterface {

    private Tower<?> parentTower;
    private TowerCard card;
    private Dice diceRequirement;

    public static final int TOWER_OCCUPIED_PAYMENT = 3;
    
    public Tile(){
    	
    }

    public TowerCard getCard() {
        return card;
    }

    public Tile(TowerCard card, Integer diceRequired, Tower<?> parentTower){
    	
    }
    
    public void setDiceRequired(Integer diceRequired){
    	diceRequirement = new Dice(ColorEnumeration.Any, diceRequired);
    }
    public Integer getDiceRequired(){

        return this.diceRequirement.getValue();
    }

    public Tower<?> getParentTower(){
        return this.parentTower;
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
    public  ArrayList<ArrayList<Resource>> getRequirements(){
        // ADDS DICE REQUIREMENT
       ArrayList<ArrayList<Resource>> req = card.getRequirements();
       for (ArrayList<Resource> andAlternative: req)
           andAlternative.add(diceRequirement);
       // ADD TOWER OCCUPIED GOLD REQUIREMENT;
       if (parentTower.isOccupied)
           for (ArrayList<Resource> andAlternative: req)
               andAlternative.add(new GoldResource(this.TOWER_OCCUPIED_PAYMENT));
       return req;
    }
  
    public boolean isOccupied() {
        return  super.isOccupied();
    }

    @Override
    public void applyEffect() {
        this.card.moveToPlayer();
        this.card.applyNonActivableEffects(this.getOccupant());
    }
}
