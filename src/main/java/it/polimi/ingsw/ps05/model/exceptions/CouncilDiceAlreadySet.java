package it.polimi.ingsw.ps05.model.exceptions;

/**
 * Created by miotto on 14/06/17.
 */
public class CouncilDiceAlreadySet extends Exception {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -680368634610601037L;

	public CouncilDiceAlreadySet() {
		System.out.println("Dado del consiglio già settato");
	}
	
}
