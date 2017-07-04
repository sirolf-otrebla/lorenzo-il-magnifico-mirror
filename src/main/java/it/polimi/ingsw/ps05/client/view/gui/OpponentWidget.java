package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;

/**
 * Created by miotto on 04/07/17.
 */
public class OpponentWidget {

    private GUIMain board;

    private boolean active; // define if it's the player's turn
    private ColorEnumeration opponentColor;
    private String opponentUsername;
    private ResourcesWidget resourceWidget = new ResourcesWidget();
    private FamiliarWidget[] familiarWidgetList = new FamiliarWidget[4];
    private LeaderWidget[] leaderWidgetList = new LeaderWidget[4];
    private BonusTileWidget bonusTileWidget;
    private CardOnPersonalWidget[][] cardAcquiredWidget = new CardOnPersonalWidget[4][6];
    private MarkerWidget[] opponentMarkers = new MarkerWidget[4];

    private final PersonalBoardWindow personalBoard = new PersonalBoardWindow(board, opponentUsername,
            cardAcquiredWidget, leaderWidgetList, bonusTileWidget);
    private final LeaderPopup leaderPopup = new LeaderPopup();

    public OpponentWidget(GUIMain board, ColorEnumeration opponentColor) {
        this.board = board;
        this.opponentColor = opponentColor;
    }




    public PersonalBoardWindow getPersonalBoard() {
        return personalBoard;
    }

    public ColorEnumeration getOpponentColor() {
        return opponentColor;
    }

    public void setOpponentColor(ColorEnumeration opponentColor) {
        this.opponentColor = opponentColor;
    }

    public ResourcesWidget getResourceWidget() {
        return resourceWidget;
    }

    public void setResourceWidget(ResourcesWidget resourceWidget) {
        this.resourceWidget = resourceWidget;
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
