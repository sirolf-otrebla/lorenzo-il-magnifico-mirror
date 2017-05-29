package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.ResourcesAndBonuses.ActionResult;
import it.polimi.ingsw.ps05.ResourcesAndBonuses.PrivilegeBonus;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 23/05/2017.
 */
public class PrivilegeBonusListener implements Observer {

    private static PrivilegeBonusListener singleton = null;


    private PrivilegeBonusListener(){
        // PRIVATE CONSTRUCTOR (SINGLETON PATTERN)
    }
    @Override
    public void update(Observable o, Object arg) {
        // TODO: take resource choice
        PrivilegeBonus privilege = (PrivilegeBonus) o;
        ArrayList<ActionResult> res = notifyToUI(privilege.getValue());
        privilege.setConvertedResources(res);
    }
    private ArrayList<ActionResult> notifyToUI(int resToChoose){
        //TODO:

        return null;
    }
    public static PrivilegeBonusListener getInstance(){
        if ( singleton == null)
            singleton = new PrivilegeBonusListener();
        return singleton;
    }
}
