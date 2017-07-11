package it.polimi.ingsw.ps05.model.cards;


import java.io.Serializable;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Epoch;

public interface Deck extends Serializable{
	
	public TowerCard getCard(Epoch epoch);
	public ColorEnumeration getDeckColor();
	
	

}
