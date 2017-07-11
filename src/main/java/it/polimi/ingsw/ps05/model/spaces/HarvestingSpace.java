package it.polimi.ingsw.ps05.model.spaces;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Familiar;
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
public class HarvestingSpace extends MultipleOccupantsActionSpace {

	/**
	 * 
	 */
	private boolean moreThanZeroOccupants = false;
	private static final long serialVersionUID = -7499949449184129806L;
	private ArrayList<Effect> effectList;

	@Override
	public ArrayList<Effect> getEffects() {
		return effectList;
	}
	
	@Override
	public boolean isOccupied(){
		if (this.getEffects() != null && this.getEffects().size() != 0){
			return false;
		}
		return super.isOccupied();
	}

	@Override
	public void applyEffect(Familiar pl) {
		Player player = pl.getRelatedPlayer();
		Iterator<GreenCard> cardListIt = player.getGreenCardList().iterator();
		while (cardListIt.hasNext())
			cardListIt.next().applyHarvestableEffects(pl);
		if (!moreThanZeroOccupants){
			moreThanZeroOccupants = true;
			this.setDiceRequirement(new Dice(ColorEnumeration.Any, this.getDiceRequirement().getValue() + 3));
		}
		this.getOccupantList().add(pl);

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
		super.setDiceRequirement(diceRequired);
		this.effectList = effectList;
	}

	@Override
	public void acceptVisitor(ViewVisitorInterface vi) {
		vi.visit(this);
	}
}
