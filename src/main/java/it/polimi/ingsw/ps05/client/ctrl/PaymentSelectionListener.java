package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.TowerTileWidget;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 28/06/2017.
 */
public class PaymentSelectionListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        TowerTileWidget tileWidget = (TowerTileWidget) arg;
    }
}