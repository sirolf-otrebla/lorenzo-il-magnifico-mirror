package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by miotto on 27/06/17.
 */
public class ProductionSpaceWidget extends ActionSpaceWidget {

    private boolean morethanZeroOccupants = false;
    private ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList; // 1 player, 2 familiare
    private boolean isLegal;

    public ProductionSpaceWidget(int minimumDie) {
        super(minimumDie);
    }



    public boolean isMorethanZeroOccupants() {
        return morethanZeroOccupants;
    }

    public void setMorethanZeroOccupants(boolean morethanZeroOccupants) {
        this.morethanZeroOccupants = morethanZeroOccupants;
    }

    public ArrayList<Pair<ColorEnumeration, ColorEnumeration>> getOccupingFamiliarList() {
        return occupingFamiliarList;
    }

    public void setOccupingFamiliarList(ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList) {
        this.occupingFamiliarList = occupingFamiliarList;
    }

}
