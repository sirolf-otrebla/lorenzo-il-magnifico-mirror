package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.gamemessages.PassActionMessage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 26/06/2017.
 */
public class PassActionListener implements Observer{


    @Override
    public void update(Observable o, Object arg) {

        PassActionMessage msg = new PassActionMessage();
        Client.getInstance().sendToServer(msg);

    }
}
