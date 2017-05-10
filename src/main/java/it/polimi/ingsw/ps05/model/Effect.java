package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

/* in preliminary UML this interface was defined as an abstract class, but there's no need to use abstract classes 
 * for this situation;
 */
public interface Effect {
	
	public EffectType getEffectType();

	public ArrayList<ArrayList<Effect>> getResultList(); // change effect with some kind of value pair object.
	
	// what other?
}
