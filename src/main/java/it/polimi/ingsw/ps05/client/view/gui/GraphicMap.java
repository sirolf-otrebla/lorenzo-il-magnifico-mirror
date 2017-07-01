package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miotto on 29/06/17.
 */
public class GraphicMap {

    public Map<Pair<ColorEnumeration, ColorEnumeration>, String> familiarColorMap = new HashMap<>();
    public Map<Integer, String> playerColorMap = new HashMap<>();

    public GraphicMap() {

        int i;

        ColorEnumeration[] plColors = new ColorEnumeration[4];
        ColorEnumeration[] famColors = new ColorEnumeration[4];
        for(i = 1; i < 5; i++)
            plColors[i-1] =  ColorEnumeration.values()[i];
        for(i = 5; i < 9; i++)
            famColors[i-5] =  ColorEnumeration.values()[i];

        /* Mapping familiar colors with related path */
        for(ColorEnumeration p : plColors)
            for(ColorEnumeration f : famColors)
                familiarColorMap.put(new Pair<>(p,f), "main/java/it.polimi.ingsw.ps05/client/view/gui/img/"
                        + p.toString() + "pl/" + f.toString() + ".png");

        i = 0;

        /* Mapping int value with player colors */
        for(ColorEnumeration p : plColors)
            playerColorMap.put(i, famColors.toString());
    }
}
