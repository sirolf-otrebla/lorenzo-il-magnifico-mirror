package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.client.ctrl.AuthResponseVisitor;

/**
 * Created by Alberto on 03/07/2017.
 */
public interface AuthResponseMessage extends NetResponseMessage {

    void acceptVisitor(AuthResponseVisitor vi);

}
