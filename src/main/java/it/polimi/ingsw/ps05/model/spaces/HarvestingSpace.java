package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Dice;

/* in preliminary UML this object was called ActivitySpace. I decided to change his name because of
 * the possible ambiguity about the term Activity (e.g. there are other objects that uses "Activity"
 * as part of their type name but are also unrelated with this. Moreover, I split the former ActivitySpace
 * in two different classes: this class and ProductionSpace class. --Sirolfo
 * 
 * further comments will be added
 */
public class HarvestingSpace extends ActionSpaceWithEffect {

    private ArrayList<Effect> effectList;

    @Override
    public ArrayList<Effect> getEffects() {
        return effectList;
    }

    @Override
    public void applyEffect() {
        Player player = this.getOccupant().getRelatedPlayer();
        Iterator<GreenCard> cardListIt = player.getGreenCardList().iterator();
        while (cardListIt.hasNext())
            cardListIt.next().applyHarvestableEffects(this.getOccupant());

    }
    
	public HarvestingSpace() {
		super();
	}

	public HarvestingSpace(ArrayList<Effect> effectList) {
		super();
		this.effectList = effectList;
	}


	public HarvestingSpace(Dice diceRequired, ArrayList<Effect> effectList) throws RepeatedAssignmentException {
		super();
		try {
            super.setDiceRequirement(diceRequired);
        } catch (RepeatedAssignmentException e) {
		    //TODO: il costruttore deve gestire l'eccezione o propagarla al controller?
        }
		this.effectList = effectList;
	}
}
