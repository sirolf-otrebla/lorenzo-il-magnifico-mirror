package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.gamemessages.PrivilegeConversionMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class PrivilegeConversionListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Integer> arrayList = (ArrayList<Integer>) arg;

    }
}
