package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;
import it.polimi.ingsw.ps05.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.resourcesandbonuses.excommunicationeffects.ExcommunicationEffect;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard{
	private ExcommunicationEffect excomEffect;
	private FaithResource faithRequested;
	private EpochEnumeration epoch;

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

	public void setEpoch(EpochEnumeration epoch) {
		this.epoch = epoch;
	}

	public void setExcomEffect(ExcommunicationEffect excomEffect) {
		this.excomEffect = excomEffect;
	}

	public void setFaithRequested(FaithResource faithRequested) {
		this.faithRequested = faithRequested;
	}
	
	public ExcommunicationEffect getExcommEffect() {
		return this.excomEffect;
	}
}
