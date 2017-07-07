package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 04/07/17.
 */
public class PlayerWidget {

    private GUIMain board;

    private boolean active; // define if it's the player's turn
    private ColorEnumeration playerColor;
    private String playerUsername;
    private ResourcesWidget resourceWidget = new ResourcesWidget();
    private FamiliarWidget[] familiarWidgetList = new FamiliarWidget[4];
    private LeaderWidget[] leaderWidgetList = new LeaderWidget[4];
    private BonusTileWidget bonusTileWidget;
    private AcquiredCardWidget[][] cardAcquiredWidget = new AcquiredCardWidget[4][6];
    private MarkerWidget[] playerMarkers = new MarkerWidget[4];
    private static HashMap<ColorEnumeration, Integer[]> AcquiredCardArrayHashMap = new HashMap<>();
    private ColorEnumeration[] personalBoardColorArray = {
            ColorEnumeration.Yellow,
            ColorEnumeration.Green,
            ColorEnumeration.Blue,
            ColorEnumeration.Violet
    };

    final VBox commands = new VBox(20 * resize);
    final Button showCardsButton = new Button("Carte sviluppo");
    final Button showLeaderButton = new Button("Carte Leader"); // showLeaderButton is not clickable because the draft is not done yet

    private final PersonalBoardWindow personalBoard = new PersonalBoardWindow(board, playerUsername,
            cardAcquiredWidget, leaderWidgetList, bonusTileWidget);
    private final LeaderPopup leaderPopup = new LeaderPopup();



    public PlayerWidget(GUIMain board, ColorEnumeration playerColor) {
        this.board = board;
        this.playerColor = playerColor;
    }

    public void addFamiliarToBoard(Pane board) {
        int i = 0;
        // initialize familiars
        for(i = 0; i < 4; i++)
            familiarWidgetList[i] = new FamiliarWidget(this.playerColor, GraphicResources.getFamiliarColor(i));
        // insert in the board
        insertDraggableFamiliar(familiarWidgetList[0], board, 66.07, 21.87);
        insertDraggableFamiliar(familiarWidgetList[1], board, 70.5357, 21.87);
        insertDraggableFamiliar(familiarWidgetList[2], board, 75, 21.87);
        insertDraggableFamiliar(familiarWidgetList[3], board, 79.4643, 21.87);
    }

    void insertDraggableFamiliar(FamiliarWidget familiar, Pane pane, double percX, double percY) {

        familiar.getImageElement().setX((percX / 100) * stageWidth);
        familiar.getImageElement().setY((percY / 100) * stageHeight);

        familiar.setupGestureSource();

        pane.getChildren().add(familiar.getImageElement());

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ColorEnumeration getPlayerColor() {
        return playerColor;
    }

    public MarkerWidget[] getPlayerMarkers() {
        return playerMarkers;
    }

    public void setPlayerMarkers(MarkerWidget[] playerMarkers) {
        this.playerMarkers = playerMarkers;
    }

    public PersonalBoardWindow getPersonalBoard() {
        return personalBoard;
    }

    public LeaderPopup getLeaderPopup() {
        return leaderPopup;
    }

    public LeaderWidget[] getLeaderWidgetList() {
        return leaderWidgetList;
    }

    public BonusTileWidget getBonusTileWidget() {
        return bonusTileWidget;
    }

    public void setBonusTileWidget(BonusTileWidget bonusTileWidget) {
        this.bonusTileWidget = bonusTileWidget;
    }

    public void setPlayerColor(ColorEnumeration playerColor) {
        this.playerColor = playerColor;
    }

    public FamiliarWidget[] getFamiliarWidgetList() {
        return familiarWidgetList;
    }

    public ResourcesWidget getResourceWidget() {
        return resourceWidget;
    }
}
