package it.polimi.ingsw.ps05.model.exceptions;

/**
 * Created by Alberto on 15/05/2017.
 */
public class RequirementsNotFullfilledException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception decorated;

    public void decorate(Exception e){
        decorated = e;
    }

}
