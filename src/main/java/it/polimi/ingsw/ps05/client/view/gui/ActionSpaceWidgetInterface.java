package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 04/07/2017.
 */
public interface ActionSpaceWidgetInterface {

    public Integer getReferenceId();

    public void setReferenceId(Integer referenceId);

    public void setLegalActionMap(HashMap<ColorEnumeration, Boolean> legalActionMap);

    public HashMap<ColorEnumeration, Boolean> getLegalActionMap();

}
