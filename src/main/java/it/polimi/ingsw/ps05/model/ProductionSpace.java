package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;
import java.util.Iterator;

/* in preliminary UML this object was called ActivitySpace. I decided to change his name because of
 * the possible ambiguity about the term Activity (e.g. there are other objects that uses "Activity"
 * as part of their type name but are also unrelated with this. Moreover, I split the former ActivitySpace
 * in two different classes: this class and HarvestingSpace class. --Sirolfo
 * 
 * further comments will be added
 */
public class ProductionSpace extends ActionSpaceWithEffect {

    private ArrayList<Effect> effectList;
    @Override
    public ArrayList<Effect> getEffects() {
        return effectList;
    }

    @Override
    public void applyEffect() {
        Player player = this.getOccupant().getRelatedPlayer();
        Iterator<YellowCard> cardListIt = player.getYellowCardList().iterator();
        while (cardListIt.hasNext())
            cardListIt.next().applyProductionEffects(this.getOccupant());


    }
}
