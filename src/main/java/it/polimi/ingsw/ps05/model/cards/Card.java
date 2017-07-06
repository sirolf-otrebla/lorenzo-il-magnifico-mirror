package it.polimi.ingsw.ps05.model.cards;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;


public interface Card extends Serializable {
	
	/**
	 * @return all the possible way to pay to take the card (some cards doesn't have requirements)
	 */
	public ArrayList<ArrayList<Resource>> getRequirements(); 
	
	/**
	 * @return the available effects for the card (every card has at least one effect).
	 */
	public ArrayList<Effect> getEffects();
	
	/** 
	 * This metod apply the non activable effects of the cards. Activable effects are activated
	 * in different way because not every card has it.
	 * @param PlayerRelated the reference to the player that is going to activate the effect
	 */
	public void applyNonActivableEffects(PlayerRelated player);
	
	/**
	 * @return the epoch in which this card will appear in the board
	 */
	public EpochEnumeration getEpoch();
	
	/**
	 * @return a String that represent the name of the card
	 */
	public String getName(); 
}
