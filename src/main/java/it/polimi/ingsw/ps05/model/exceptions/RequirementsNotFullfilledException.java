package it.polimi.ingsw.ps05.model.exceptions;

import java.lang.reflect.Executable;

/**
 * Created by Alberto on 15/05/2017.
 */
public class RequirementsNotFullfilledException extends Exception {

    private Exception decorated;

    public void decorate(Exception e){
        decorated = e;
    }

}
