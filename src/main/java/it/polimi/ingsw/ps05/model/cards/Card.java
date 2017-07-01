package it.polimi.ingsw.ps05.model.cards;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.PlayerRelated;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.*;


public interface Card extends Serializable {

	public ArrayList<ArrayList<Resource>> getRequirements(); 
	
	public ArrayList<Effect> getEffects();

	public void applyNonActivableEffects(PlayerRelated player);
	
	public EpochEnumeration getEpoch();
	
	public String getName(); 
	
	public Integer getId();
}
