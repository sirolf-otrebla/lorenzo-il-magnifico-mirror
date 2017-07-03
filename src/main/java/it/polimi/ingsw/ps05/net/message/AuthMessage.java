package it.polimi.ingsw.ps05.net.message;

import it.polimi.ingsw.ps05.server.controller.AuthListener;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

/**
 * Created by Alberto on 02/07/2017.
 */
public interface AuthMessage extends NetMessage {


    void acceptVisitor(AuthListener vi);
}
