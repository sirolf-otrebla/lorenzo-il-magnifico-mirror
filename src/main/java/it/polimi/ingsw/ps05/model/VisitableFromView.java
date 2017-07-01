package it.polimi.ingsw.ps05.model;

import it.polimi.ingsw.ps05.client.ctrl.ViewVisitorInterface;

/**
 * Created by Alberto on 30/06/2017.
 */
public interface VisitableFromView {

    void acceptVisitor(ViewVisitorInterface vi);
}
