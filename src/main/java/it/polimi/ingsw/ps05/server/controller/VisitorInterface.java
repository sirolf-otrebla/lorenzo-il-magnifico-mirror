package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;

/**
 * Created by Alberto on 19/06/2017.
 */
public interface VisitorInterface {

    public void visit(ActionMessage mess);

    public void visit(ExitGameMessage mess);
}
