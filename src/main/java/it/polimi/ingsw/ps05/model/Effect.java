package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;

import java.util.ArrayList;

/* in preliminary UML this interface was defined as an abstract class, but there's no need to use abstract classes 
 * for this situation;
 */
public interface Effect {
	
	public EffectType getEffectType();

	public ArrayList<ArrayList<ActionResult>> getResultList();
	
	// what other?
}
