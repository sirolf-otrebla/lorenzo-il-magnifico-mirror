package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.gui.TowerTileWidget;
import it.polimi.ingsw.ps05.client.view.interfaces.TowerTileViewObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 28/06/2017.
 */
public class PaymentSelectionListener implements Observer {
	//TODO not used
    @Override
    public void update(Observable o, Object arg) {
        TowerTileViewObject tileWidget = (TowerTileViewObject) arg;
    }
}
