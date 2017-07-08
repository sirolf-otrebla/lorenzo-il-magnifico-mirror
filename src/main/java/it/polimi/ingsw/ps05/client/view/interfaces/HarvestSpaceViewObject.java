package it.polimi.ingsw.ps05.client.view.interfaces;

import java.util.ArrayList;

/**
 * Created by Alberto on 01/07/2017.
 */
public abstract class HarvestSpaceViewObject extends ActionSpaceViewObject{

    public abstract ArrayList<Integer> getActiveCardsId();
    public abstract ArrayList<Integer> optionForCards();
}
