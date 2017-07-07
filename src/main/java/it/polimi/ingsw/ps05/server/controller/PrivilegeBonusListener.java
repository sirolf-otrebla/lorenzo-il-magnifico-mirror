package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.client.ctrl.ViewAdapter;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.PrivilegeBonus;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 23/05/2017.
 */
public class PrivilegeBonusListener implements Observer {

    private static PrivilegeBonusListener singleton = null;
    
    ArrayList<ArrayList<ActionResult>> conversionList = new ArrayList<ArrayList<ActionResult>>();


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
    	return ViewAdapter.getInstance().showPrivilegeConversion(conversionList, resToChoose);
        
    }
    public static PrivilegeBonusListener getInstance(){
        if ( singleton == null)
            singleton = new PrivilegeBonusListener();
        return singleton;
    }
    
    public void setConversionResource(ArrayList<ArrayList<ActionResult>> list){
    	this.conversionList = list;
    }
}
