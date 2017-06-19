package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;
import it.polimi.ingsw.ps05.net.message.LeaderCardMessage;
import it.polimi.ingsw.ps05.net.server.NetMessageVisitor;

/**
 * Created by Alberto on 19/06/2017.
 */
public interface VisitorInterface extends NetMessageVisitor{

    public void visit(ActionMessage mess);

    public void visit(ExitGameMessage mess);

    public void visit(LeaderCardMessage lCardMsg);
}
