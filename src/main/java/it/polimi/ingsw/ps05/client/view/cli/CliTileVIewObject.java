package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.TowerTileViewObject;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;

/**
 * Created by Alberto on 01/07/2017.
 */
public class CliTileVIewObject extends TowerTileViewObject {
    private TowerTileInterface tile;
    private Integer payment;
    private ColorEnumeration familyMemberID;

    public CliTileVIewObject(TowerTileInterface tile, ColorEnumeration familyMemberID, Integer payment){
        this.tile = tile;
        this.payment = payment;
        this.familyMemberID = familyMemberID;
        Client.getInstance().linkToObserver(this);
    }

    @Override
    public boolean hasMorePaymentOptions() {
        return tile.hasMorePaymentOptions();
    }

    @Override
    public Integer getSelectedPayment() {
        return this.payment;
    }

    @Override
    public ColorEnumeration getFamilyMemberID() {
        return this.familyMemberID;
    }

    @Override
    public Integer getId() {
        return tile.getId();
    }

}
