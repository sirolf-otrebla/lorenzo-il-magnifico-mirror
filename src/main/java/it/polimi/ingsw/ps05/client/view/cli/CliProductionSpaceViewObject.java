package it.polimi.ingsw.ps05.client.view.cli;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.view.interfaces.ProductionSpaceViewObject;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.ProductionSpace;


import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alberto on 02/07/2017.
 */
public class CliProductionSpaceViewObject extends ProductionSpaceViewObject {
    private ArrayList<Integer> activeCardIds;
    private ProductionSpace space;
    private ColorEnumeration familyMemberID;
    private ArrayList<Integer> activationOptionForCard;

    public CliProductionSpaceViewObject(ProductionSpace space, ColorEnumeration familyMemberID,
                                     ArrayList<Integer> activeCardIds, ArrayList<Integer> activationOptionForCard){
        this.space = space;
        this.activeCardIds = activeCardIds;
        this.familyMemberID = familyMemberID;
        this.activationOptionForCard = activationOptionForCard;
        Client.getInstance().linkToObserver(this);

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
    	System.out.println("notifico all'observer");
        setChanged();
        notifyObservers(this);
    }

	@Override
	public ArrayList<Integer> optionForCards() {
		return this.activationOptionForCard;
	}
}