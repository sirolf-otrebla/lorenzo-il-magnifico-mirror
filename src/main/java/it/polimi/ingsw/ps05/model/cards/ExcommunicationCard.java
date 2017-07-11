package it.polimi.ingsw.ps05.model.cards;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.EpochEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.excommunicationeffects.ExcommunicationEffect;


// in this case we have to think if it's useful or not to use the card Interface --Sirolfo

/** this class represents an Excommunication card, as in the real game.
 *
 */
public class ExcommunicationCard implements Serializable {
	/**
	 * this is the unique reference id tho this Excommunication card. it is loaded from file.
	 */
	private Integer referenceID;
	private static final long serialVersionUID = 8859721604132988242L;
	/**
	 *
	 */
	private ExcommunicationEffect excomEffect;
	private FaithResource faithRequested;
	private EpochEnumeration epoch;

	public ExcommunicationCard(FaithResource faithRequested, EpochEnumeration epoch,
							   ExcommunicationEffect excomEffect) {
		this.faithRequested = faithRequested;
		this.epoch = epoch;
		this.excomEffect = excomEffect;
	}

	/** this method return the amount that the excommunication card needs to be avoided.
	 *
	 * @return	returns a {@link FaithResource} object that represent the exact amount of faith needed to avoid
	 *			excommunication
	 */
	public FaithResource getFaithRequested() {
		return faithRequested;
	}

	/** this method applies the {@link ExcommunicationEffect} of this card to the player, passed by parameter
	 *
	 * @param pl	this is the {@link Player} that has to be excommunicated
	 */
	public void applyEffect(Player pl){
		excomEffect.apply(pl);
	}

	/** this method returns the era id of this excommunication card
	 *
	 * @return an {@link EpochEnumeration} that represent the era of this Excommunication card
	 */
	public EpochEnumeration getEpochID() {
		return this.epoch;
	}

	/** this method sets the Era of this card
	 *
	 * @param epoch this is the {@link EpochEnumeration} object representing the era that is going to be set.
	 */
	public void setEpoch(EpochEnumeration epoch) {
		this.epoch = epoch;
	}

	public void setExcomEffect(ExcommunicationEffect excomEffect) {
		this.excomEffect = excomEffect;
	}

	public void setFaithRequested(FaithResource faithRequested) {
		this.faithRequested = faithRequested;
	}

	/** this method returns the effect of this excommunication card.
	 *
	 * @return returns and {@link ExcommunicationEffect} representing the effect of thi excommunication card
	 */
	public ExcommunicationEffect getExcommEffect() {
		return this.excomEffect;
	}

	/** returns the unique Reference ID of this object
	 *
	 * @return returns the unique Reference ID of this object
	 */
	public Integer getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(Integer referenceID) {
		this.referenceID = referenceID;
	}
}
