package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class PersonalBoardWindow {

    public static final int ORIGINAL_WIDTH = 3459, ORIGINAL_HEIGHT = 3800, ORIGINAL_RATIO = ORIGINAL_WIDTH / ORIGINAL_HEIGHT;
    public static double personalBoardWidth, personalBoardHeight, personalBoardResize;

    GUIMain board;
    String username;
    CardOnPersonalWidget[][] cardAcquiredWidget = new CardOnPersonalWidget[4][4];
    LeaderWidget[] leaderWidgets;
    BonusTileWidget bonusTile;

    private HBox[] cardHboxes = new HBox[4];


    public PersonalBoardWindow(GUIMain board, String username, CardOnPersonalWidget[][] cardAcquiredWidget,
                               LeaderWidget[] leaderWidgets, BonusTileWidget bonusTile) {
        this.board = board;
        this.username = username;
        this.cardAcquiredWidget = cardAcquiredWidget;
        this.leaderWidgets = leaderWidgets;
        this.bonusTile = bonusTile;
    }

    public void display() {

        Stage stage = new Stage();

        stage.setTitle("My Board");
        stage.setResizable(false);
        stage.centerOnScreen();

        // calculate and set window dimensions
        PersonalBoardWindow.personalBoardHeight = stageHeight * 0.90;
        PersonalBoardWindow.personalBoardWidth = (stageWidth * 0.90 * ORIGINAL_RATIO);
        stage.setHeight(PersonalBoardWindow.personalBoardHeight);
        stage.setWidth(PersonalBoardWindow.personalBoardWidth);
        personalBoardResize = personalBoardWidth / ORIGINAL_WIDTH;

        final Pane pane = new Pane();
        pane.setId("personalBoard");

        /* add button that shows bonus tile */
        Button showBonusTileButton = new Button("Bonus tile");
        showBonusTileButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showBonusTileButton.setOnAction((ActionEvent e) -> {
            //TODO implementare popup
        });

        /*
        pane.minWidthProperty().bind(stage.widthProperty());
        pane.minHeightProperty().bind(stage.heightProperty());
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.maxWidthProperty().bind(stage.widthProperty());
        pane.maxHeightProperty().bind(stage.heightProperty());
        */

        /* show acquired cards */
        setCardAcquiredLayout();

        Scene personalScene = new Scene(pane);

        File f = new File("./src/main/res/fx-style.css");
        try {
            personalScene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        stage.setScene(personalScene);
        stage.show();
    }

    void setCardAcquiredLayout() {

        for(int i = 0; i < 4; i++) {
            // inside the i hbox
            this.cardHboxes[i] = new HBox(20 * personalBoardResize); //TODO controllare che lo spacing sia corretto
            this.cardHboxes[i].setLayoutX((2.4277 / 100) * personalBoardWidth);
            this.cardHboxes[i].setLayoutY((1.8684 + 25.5 * i) / 100 * personalBoardHeight);

            this.cardHboxes[i].setPrefHeight((21.0 / 100) * personalBoardHeight);
            this.cardHboxes[i].setPrefWidth((90.0 / 100) * personalBoardWidth);
            for(int j = 0; j < 4; j++) {
                // inside the j color of cards acquired
                for(int k = 0; k < 4; k++) {
                    // inside the k card of j color
                    if(cardAcquiredWidget[j][k] != null)
                        cardHboxes[i].getChildren().add(cardAcquiredWidget[j][k].getCardImage());
                }
            }
        }

    }

    /***   Metodo per Sirolfo   ***/
    public void repaint() {

        TowerTileWidget[][] towerActionSpaces = board.getTowerTileWidgetList();

        for (TowerTileWidget[] tower: towerActionSpaces) {
            for (TowerTileWidget actionSpace: tower) {
                if(actionSpace.getAssociatedCard().isTaken() && actionSpace.getOccupantPlayerColor() == board.getPlayer().getPlayerColor()) {
                    // enter if the card is taken with a familiar of the player
                    actionSpace.getAssociatedCard().addToPersonalBoard(this);
                }
            }
        }

    }

    public HBox[] getCardHboxes() {
        return cardHboxes;
    }
}
