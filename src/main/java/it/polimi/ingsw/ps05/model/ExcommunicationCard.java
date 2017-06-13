package it.polimi.ingsw.ps05.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ExcommunicationEffects.ExcommunicationEffect;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.Resource;
import it.polimi.ingsw.ps05.model.exceptions.RepeatedAssignmentException;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard{

	private EpochEnumeration epoch = EpochEnumeration.NO_EPOCH;

	public EpochEnumeration getEpoch() {
		return this.epoch;
	}

	public void setEpoch(EpochEnumeration epoch) throws RepeatedAssignmentException {
		if (this.epoch == EpochEnumeration.NO_EPOCH) {
			this.epoch = epoch;
		} else {
			throw new RepeatedAssignmentException();
		}
	}

}
