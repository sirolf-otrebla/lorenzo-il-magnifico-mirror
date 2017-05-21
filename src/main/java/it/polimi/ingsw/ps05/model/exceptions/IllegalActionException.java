package it.polimi.ingsw.ps05.model.exceptions;

/**
 * Created by Alberto on 18/05/2017.
 */
public class IllegalActionException extends Exception {

    public static final int BAD_PAY_CHOICE = 0xCA220;
    public static final int ACTION_NOT_LEGAL = 0x0DD1;

    private final int flag;

    public IllegalActionException(int flag){
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
