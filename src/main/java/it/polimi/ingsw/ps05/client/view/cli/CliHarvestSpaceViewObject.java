package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.HarvestSpaceViewObject;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class CliHarvestSpaceViewObject extends HarvestSpaceViewObject {
    private ArrayList<Integer> activeCardIds;
    private HarvestingSpace space;
    private ColorEnumeration familyMemberID;
    private ArrayList<Integer> optionForCards;

    public CliHarvestSpaceViewObject(HarvestingSpace space, ColorEnumeration familyMemberID,
                                     ArrayList<Integer> activeCardIds, ArrayList<Integer> optionForCards){
        this.space = space;
        this.activeCardIds = activeCardIds;
        this.familyMemberID = familyMemberID;
        Client.getInstance().linkToObserver(this);
    }
    
    @Override
    public ArrayList<Integer> optionForCards(){
    	return this.optionForCards;
    }

    @Override
    public ArrayList<Integer> getActiveCardsId() {
        return this.activeCardIds;
    }

    @Override
    public ColorEnumeration getFamilyMemberID() {
        return this.familyMemberID;
    }

    @Override
    public Integer getId() {
        return this.space.getId();
    }

    @Override
    public void notifyToActionHandler() {
        setChanged();
        notifyObservers(this);
    }
}
