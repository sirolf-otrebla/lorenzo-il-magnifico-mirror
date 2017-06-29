package it.polimi.ingsw.ps05.model;

import java.io.Serializable;

import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;

public class Epoch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7674672173965107029L;
	private EpochEnumeration epoch;
	private ExcommunicationCard excommunicationCard;

	public Epoch(EpochEnumeration epoch) {
		this.epoch = epoch;
	}
	
	public Epoch() {
		
	}
	
	public Epoch(EpochEnumeration epoch, ExcommunicationCard excommunicationCard){
		this.epoch = epoch;
		this.excommunicationCard = excommunicationCard;
	}
	
	public EpochEnumeration getID() {
		return this.epoch;
	}
	
	public ExcommunicationCard getExcomunicationCard(){
		return this.excommunicationCard;
	}

	@Override
	public String toString() {
		return "Epoch [epoch=" + epoch + "]";
	}
	
	
}
