package it.polimi.ingsw.ps05.model.exceptions;

/**
 * Created by miotto on 09/06/17.
 */
public class RepeatedAssignmentException extends Exception {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Already set";
    }

}
