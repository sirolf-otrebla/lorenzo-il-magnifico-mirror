package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by miotto on 27/06/17.
 */
public class CouncilSpaceWidget extends ActionSpaceWidget{
    private ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList;

    public CouncilSpaceWidget(int minimumDie) {
        super(minimumDie);
    }

    public ArrayList<Pair<ColorEnumeration, ColorEnumeration>> getOccupingFamiliarList() {
        return occupingFamiliarList;
    }

    public void setOccupingFamiliarList(ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList) {
        this.occupingFamiliarList = occupingFamiliarList;
    }
}
