package it.polimi.ingsw.ps05.client.view.interfaces;

import it.polimi.ingsw.ps05.model.ColorEnumeration;

import java.util.Observable;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class ActionSpaceViewObject extends Observable{
    public abstract ColorEnumeration getFamilyMemberID();
    public abstract Integer getId();


}
