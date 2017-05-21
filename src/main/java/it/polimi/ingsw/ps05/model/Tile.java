package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.Dice;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.GoldResource;
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
    private Dice diceRequirement;

    public static final int TOWER_OCCUPIED_PAYMENT = 3;

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

    @Override
    public void applyEffect() {

    }
}
