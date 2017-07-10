package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;

import java.util.ArrayList;

/**
 * Created by Alberto on 04/07/2017.
 */
public interface ActionSpaceWidgetInterface {

    public Integer getReferenceId();

    public void setReferenceId(Integer referenceId);

    public void setLegalFamilyMemberList(ArrayList<ColorEnumeration> legalFamilyMemberList);

    public ArrayList<ColorEnumeration> getLegalFamilyMemberList();

    public boolean isLegal();

    public void setLegal(boolean legal);



}
