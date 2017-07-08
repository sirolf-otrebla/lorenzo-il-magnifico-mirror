package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.control.Button;

import java.util.HashMap;

/**
 * Created by miotto on 04/07/17.
 */
public class OpponentWidget {

    private GUIMain board;

    private boolean active; // define if it's the player's turn
    private ColorEnumeration opponentColor;
    private String opponentUsername;
    private FamiliarWidget[] familiarWidgetList = new FamiliarWidget[4];
    private LeaderWidget[] leaderWidgetList = new LeaderWidget[4];
    private AcquiredCardWidget[][] cardAcquiredWidget = new AcquiredCardWidget[4][6];
    private MarkerWidget[] opponentMarkers = new MarkerWidget[4];
    private static HashMap<ColorEnumeration, Integer[]> AquiredCardArrayHashMap = new HashMap<>();

    private final OpponentBoardWindow opponentBoard = new OpponentBoardWindow(board, opponentUsername,
            leaderWidgetList);
    private final LeaderPopup leaderPopup = new LeaderPopup();

    final Button personalBoardButton = new Button();




    public OpponentWidget(GUIMain board, ColorEnumeration opponentColor) {
        this.board = board;
        this.opponentColor = opponentColor;
    }


    public OpponentBoardWindow getPersonalBoard() {
        return opponentBoard;
    }

    public ColorEnumeration getOpponentColor() {
        return opponentColor;
    }

    public void setOpponentColor(ColorEnumeration opponentColor) {
        this.opponentColor = opponentColor;
    }

    public FamiliarWidget[] getFamiliarWidgetList() {
        return familiarWidgetList;
    }

    public void setFamiliarWidgetList(FamiliarWidget[] familiarWidgetList) {
        this.familiarWidgetList = familiarWidgetList;
    }

    public MarkerWidget[] getOpponentMarkers() {
        return opponentMarkers;
    }

    public void setOpponentMarkers(MarkerWidget[] opponentMarkers) {
        this.opponentMarkers = opponentMarkers;
    }

}
