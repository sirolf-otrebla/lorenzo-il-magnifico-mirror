package it.polimi.ingsw.ps05.model.cards;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.ExcommunicationEffect;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

public class ExcommunicationCard implements Serializable {
	/**
	 * 
	 */
	private Integer referenceID;
	private static final long serialVersionUID = 8859721604132988242L;
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
		excomEffect.apply(pl);
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

	public Integer getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
}
