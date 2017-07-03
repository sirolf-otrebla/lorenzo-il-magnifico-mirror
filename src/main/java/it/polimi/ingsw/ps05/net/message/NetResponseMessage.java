package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.net.ClientMessageTaker;

/**
 * Created by Alberto on 03/07/2017.
 */
public interface NetResponseMessage extends NetMessage {

    void acceptVisitor(ClientMessageTaker vi);
}
