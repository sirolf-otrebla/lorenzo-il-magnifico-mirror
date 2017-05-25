package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ExcommunicationEffects.ExcommunicationEffect;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard{

	private EpochEnumeration epoch;
	private ExcommunicationEffect excomEffect;

	public EpochEnumeration getEpoch() {
		return this.epoch;
	}
	
	public ExcommunicationEffect getExcomEffect(){ return this.excomEffect; }

	public void setEpoch(EpochEnumeration epoch) { this.epoch = epoch; }

	public void setExcomEffect(ExcommunicationEffect excomEffect) { this.excomEffect = excomEffect; }

}
