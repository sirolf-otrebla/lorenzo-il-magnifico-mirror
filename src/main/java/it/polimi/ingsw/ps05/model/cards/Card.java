package it.polimi.ingsw.ps05.model.cards;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;


public interface Card {

	public ArrayList<ArrayList<Resource>> getRequirements(); 
	
	public ArrayList<Effect> getEffects();

	//better to use Familiar?
	public void applyNonActivableEffects(PlayerRelated player);
	
	public EpochEnumeration getEpoch();
	
	public String getName();
	
	
	
	//TODO: evaluate if it's better to use a resource object or a value pair object as in preliminary UML -- Sirolfo 
}
