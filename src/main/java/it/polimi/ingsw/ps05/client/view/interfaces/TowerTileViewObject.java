package it.polimi.ingsw.ps05.client.view.interfaces;

import java.util.Observable;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class TowerTileViewObject extends ActionSpaceViewObject {

    public abstract boolean hasMorePaymentOptions();

    public abstract  Integer getSelectedPayment();


}
