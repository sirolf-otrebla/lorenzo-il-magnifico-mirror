package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.resourcesandbonuses.ExcommunicationEffects.ExcommunicationEffect;
import it.polimi.ingsw.ps05.resourcesandbonuses.FaithResource;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard{
	private FaithResource faithRequested;
	private EpochEnumeration epoch;
	private ExcommunicationEffect excomEffect;

	public ExcommunicationCard(FaithResource faithRequested, EpochEnumeration epoch,
							   ExcommunicationEffect excomEffect) {
		this.faithRequested = faithRequested;
		this.epoch = epoch;
		this.excomEffect = excomEffect;
	}

	public FaithResource getFaithRequested() {
		return faithRequested;
	}

	public void applyEffect(Player pl){

		//TODO:
	}

	public EpochEnumeration getEpochID() {
		return this.epoch;
	}
	
	public ExcommunicationEffect getExcomEffect(){
		return this.excomEffect;
	}

	public void setEpoch(EpochEnumeration epoch) {
		this.epoch = epoch;
	}

	public void setExcomEffect(ExcommunicationEffect excomEffect) {
		this.excomEffect = excomEffect;
	}

}
