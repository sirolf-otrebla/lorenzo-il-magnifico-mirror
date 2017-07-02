package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.spaces.HarvestingSpace;
import javafx.util.Pair;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miotto on 29/06/17.
 */
public class GraphicResources {

    public Map<Integer, String> playerColorMap = new HashMap<>(); // che cazzo è sta merda

    static HashMap<ColorEnumeration, HashMap<ColorEnumeration, String>> familiarResPath = new HashMap<>();

    static HashMap<Integer, ColorEnumeration> diceColorMap = new HashMap<>();

    static HashMap<Integer, ColorEnumeration> cardColorMap = new HashMap<>();



    public GraphicResources() {
        buildFamiliarPathsMap();
        buildDiceColorMap();
        buildCardColorMap();
    }

    private void buildFamiliarPathsMap() {
        int i;
        ColorEnumeration[] plColors = new ColorEnumeration[4];
        ColorEnumeration[] famColors = new ColorEnumeration[4];
        for (i = 1; i < 5; i++)
            plColors[i - 1] = ColorEnumeration.values()[i];
        for (i = 5; i < 9; i++)
            famColors[i - 5] = ColorEnumeration.values()[i];

        /* Mapping familiar colors with related path */
        for (ColorEnumeration p : plColors){
            HashMap<ColorEnumeration, String> pathMap = new HashMap<>();
            for (ColorEnumeration f : famColors) {
                pathMap.put(f, "main/java/it.polimi.ingsw.ps05/client/view/gui/img/"
                        + p.toString() + "pl/" + f.toString() + ".png");
            }
            this.familiarResPath.put(p, pathMap);
        }

    }

    private void buildDiceColorMap() {
        int i;
        ColorEnumeration[] famColors = new ColorEnumeration[4];
        for (i = 5; i < 9; i++)
            famColors[i - 5] = ColorEnumeration.values()[i];
        i = 0;
        for (ColorEnumeration f: famColors) {
            this.diceColorMap.put(i, f);
        }
    }

    private void buildCardColorMap() {
        this.cardColorMap.put(0, ColorEnumeration.Green);
        this.cardColorMap.put(1, ColorEnumeration.Blue);
        this.cardColorMap.put(2, ColorEnumeration.Yellow);
        this.cardColorMap.put(3, ColorEnumeration.Violet);
    }

    public String getFamiliarPath(ColorEnumeration player, ColorEnumeration familyMember){
        return familiarResPath.get(player).get(familyMember);

    }

    public ColorEnumeration getDiceColor(int i) {
        return diceColorMap.get(i);
    }

    public ColorEnumeration getCardColor(int i) {
        return cardColorMap.get(i);
    }
}
