package it.polimi.ingsw.ps05.model.effects;

import java.io.Serializable;

/* in preliminary UML this interface was defined as an abstract class, but there's no need to use abstract classes
 * for this situation;
 */
public interface Effect extends Serializable {
	
	public EffectType getEffectType();
	
	// what other?
}
