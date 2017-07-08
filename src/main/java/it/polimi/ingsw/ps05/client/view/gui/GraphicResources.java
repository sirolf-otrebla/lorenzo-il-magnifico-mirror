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
public final class GraphicResources {

    private static HashMap<ColorEnumeration, HashMap<ColorEnumeration, String>> familiarResPath = new HashMap<>();

    private static HashMap<Integer, ColorEnumeration> diceColorMap = new HashMap<>();

    private static HashMap<Integer, ColorEnumeration> cardColorMap = new HashMap<>();

    private static HashMap<Integer, String> leaderPathMap = new HashMap<>();

    private static HashMap<Integer, ColorEnumeration> playerColorMap = new HashMap<>();

    private static HashMap<Integer, ColorEnumeration> familiarColorMap = new HashMap<>();

    private static HashMap<Integer, String> bonusTilePathMap = new HashMap<>();

    private static HashMap<Integer, String> developmentCardPathMap = new HashMap<>();

    private static HashMap<ColorEnumeration, Integer[]> TowerCardArrayHashMap = new HashMap<>();

    private static HashMap<Integer, String> excomPathMap = new HashMap<>();

    private static HashMap<ColorEnumeration, String> markerPathMap = new HashMap<>();

    private static HashMap<String, Integer> resourcesMap = new HashMap<>();



    GraphicResources() {
        buildFamiliarPathsMap();
        buildDiceColorMap();
        buildCardColorMap();
        buildLeaderPathMap();
        buildPlayerColorMap();
        buildFamiliarColorMap();
        buildBonusTilePathMap();
        buildDevelopmentCardPathMap();
        buildExcomPathMap();
        buildMarkerPathMap();
        buildResourcesMap();
    }

    private static void buildResourcesMap() {
        resourcesMap.put("Oro", 0);
        resourcesMap.put("Legno", 1);
        resourcesMap.put("Pietra", 2);
        resourcesMap.put("Servitori", 3);
    }

    private static void buildMarkerPathMap() {
        markerPathMap.put(ColorEnumeration.Red, "./src/main/res/img/marker-red.png");
        markerPathMap.put(ColorEnumeration.Green, "./src/main/res/img/marker-green.png");
        markerPathMap.put(ColorEnumeration.Blue, "./src/main/res/img/marker-blue.png");
        markerPathMap.put(ColorEnumeration.Yellow, "./src/main/res/img/marker-yellow.png");
    }

    private static void buildExcomPathMap() {
        for(int i = 0; i < 21; i++) {
            int epoch = (i / 7) + 1;
            excomPathMap.put(i, "./src/main/res/img/excom/excomm_" + epoch + "_" + ((i % 7) + 1) + ".png");
        }
    }

    private static void buildDevelopmentCardPathMap() {
        for(int i = 0; i < 96; i++)
            developmentCardPathMap.put(i, "./src/main/res/img/cards/devcards_f_en_c_" + (i + 1) + ".png");
    }

    private static void buildBonusTilePathMap() {
        bonusTilePathMap.put(4, "./src/main/res/img/bonusTiles/bonustile_STANDARD");
        for(int i = 0; i < 4; i++) {
            bonusTilePathMap.put(i, "./src/main/res/img/bonusTiles/bonustile_" + (i + 1) + ".png");
        }
    }

    private static void buildLeaderPathMap() {
        for(int i = 1; i <= 20; i++)
            if(i < 10)
                leaderPathMap.put(i, "./src/main/res/img/cards/leaders/leaders_f_c_0" + i + ".jpg");
            else
                leaderPathMap.put(i, "./src/main/res/img/cards/leaders/leaders_f_c_" + i + ".jpg");
    }

    private static void buildFamiliarPathsMap() {
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
                pathMap.put(f, "./src/main/res/img/"
                        + p.toString().toLowerCase() + "pl/" + f.toString() + ".png");
            }
            familiarResPath.put(p, pathMap);
        }

    }

    private static void buildDiceColorMap() {

        diceColorMap.put(0, ColorEnumeration.Black);
        diceColorMap.put(1, ColorEnumeration.White);
        diceColorMap.put(2, ColorEnumeration.Orange);
        /*
        int i;
        ColorEnumeration[] famColors = new ColorEnumeration[4];
        for (i = 5; i < 9; i++)
            famColors[i - 5] = ColorEnumeration.values()[i];
        i = 0;
        for (ColorEnumeration f: famColors) {
            diceColorMap.put(i, f);
        }
        */
    }

    private static void buildCardColorMap() {
        cardColorMap.put(0, ColorEnumeration.Green);
        cardColorMap.put(1, ColorEnumeration.Blue);
        cardColorMap.put(2, ColorEnumeration.Yellow);
        cardColorMap.put(3, ColorEnumeration.Violet);
    }

    private static void buildPlayerColorMap() {
        playerColorMap.put(0, ColorEnumeration.Red);
        playerColorMap.put(1, ColorEnumeration.Green);
        playerColorMap.put(2, ColorEnumeration.Blue);
        playerColorMap.put(3, ColorEnumeration.Yellow);
    }

    private static void buildFamiliarColorMap() {
        familiarColorMap.put(0, ColorEnumeration.Black);
        familiarColorMap.put(1, ColorEnumeration.White);
        familiarColorMap.put(2, ColorEnumeration.Orange);
        familiarColorMap.put(3, ColorEnumeration.Any);
    }



    public static ColorEnumeration getPlayerColor(int i) {
        return playerColorMap.get(i);
    }

    public static ColorEnumeration getFamiliarColor(int i) {
        return familiarColorMap.get(i);
    }

    public static String getFamiliarPath(ColorEnumeration player, ColorEnumeration familyMember){
        return familiarResPath.get(player).get(familyMember);
    }

    public static String getLeaderPath(int i) {
        return leaderPathMap.get(i);
    }

    public static ColorEnumeration getDiceColor(int i) {
        return diceColorMap.get(i);
    }

    public static ColorEnumeration getCardColor(int i) {
        return cardColorMap.get(i);
    }

    public static String getBonusTilePath(int i) { return bonusTilePathMap.get(i); }

    public static String getCardPath(int i) {
        return developmentCardPathMap.get(i);
    }

    public static String getExcomPath(int i) { return excomPathMap.get(i); }

    public static Integer getResource(String id) {
        return resourcesMap.get(id);
    }

    public static String getMarkerPath(ColorEnumeration playerColor) {
        return markerPathMap.get(playerColor);
    }
}
