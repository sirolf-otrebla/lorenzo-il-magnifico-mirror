package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.resourcesandbonuses.ExcommunicationEffects.ExcommunicationEffect;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard{

	private EpochEnumeration epoch = EpochEnumeration.NO_EPOCH;

	public EpochEnumeration getEpoch() {
		return this.epoch;
	}
	
	public ExcommunicationEffect getExcomEffect(){
		return this.excomEffect;
	}

	public void setEpoch(EpochEnumeration epoch) throws RepeatedAssignmentException {
		if (this.epoch == EpochEnumeration.NO_EPOCH) {
			this.epoch = epoch;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

}
