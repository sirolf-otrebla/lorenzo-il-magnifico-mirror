package it.polimi.ingsw.ps05.client.view.gui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    AcquiredCardWidget[][] cardAcquiredWidget = new AcquiredCardWidget[4][6];
    LeaderWidget[] leaderWidgets;
    BonusTileWidget bonusTile;

    private HBox[] cardHboxes;


    public PersonalBoardWindow(GUIMain board, String username, AcquiredCardWidget[][] cardAcquiredWidget,
                               LeaderWidget[] leaderWidgets, BonusTileWidget bonusTile) {
        this.board = board;
        this.username = username;
        this.cardAcquiredWidget = cardAcquiredWidget;
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 6; j++)
                this.cardAcquiredWidget[i][j] = new AcquiredCardWidget(); // creating empty card widgets
        this.leaderWidgets = leaderWidgets;
        this.bonusTile = bonusTile;
    }

    public void display() {

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        //stage.setTitle("My Board");
        //stage.setResizable(false);

        // calculate and set window dimensions
        personalBoardHeight = stageHeight * 0.90;
        personalBoardWidth = (stageHeight * 0.90) * ORIGINAL_RATIO;
        personalBoardResize = personalBoardWidth / ORIGINAL_WIDTH;

        stage.setHeight(personalBoardHeight);
        stage.setWidth(personalBoardWidth);

        final Pane pane = new Pane();
        pane.setId("personalBoard");

        /* Add button for bonus tile */
        Button showBonusTileButton = new Button("Bonus tile");
        // showBonusTileButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showBonusTileButton.setOnAction((ActionEvent e) -> {
            //TODO implementare popup
        });

        /* Add Close button */
        Button closeButton = new Button("Close");
        closeButton.layoutXProperty().bind(stage.widthProperty().multiply(90 / 100));
        closeButton.layoutYProperty().bind(stage.heightProperty().multiply(1 / 100));
        closeButton.setOnAction((ActionEvent e) -> {
            stage.close();
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
        cardHboxes = new HBox[4];
        setCardAcquiredLayout();

        Scene personalScene = new Scene(pane);

        File f = new File("./src/main/res/fx-style.css");
        try {
            personalScene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        stage.centerOnScreen();
        stage.setScene(personalScene);
        stage.showAndWait();
    }

    void setCardAcquiredLayout() {

        for(int i = 0; i < 4; i++) {
            // inside the i hbox
            //cardHboxes[i] = new HBox(20 * personalBoardResize);
            cardHboxes[i] = new HBox(20); //TODO controllare che lo spacing sia corretto
            cardHboxes[i].setFillHeight(true);
            cardHboxes[i].setLayoutX((2.4277 / 100) * personalBoardWidth);
            cardHboxes[i].setLayoutY((1.8684 + 25.5 * i) / 100 * personalBoardHeight);

            cardHboxes[i].setPrefHeight((21.0 / 100) * personalBoardHeight);
            cardHboxes[i].setPrefWidth((90.0 / 100) * personalBoardWidth);
            for(int j = 0; j < 4; j++) {
                // inside the j color of cards acquired
                for(int k = 0; k < 4; k++) {
                    // inside the k card of j color
                    if(cardAcquiredWidget[j][k].getCardImage().getImage() != null)
                        cardHboxes[i].getChildren().add(cardAcquiredWidget[j][k].getCardImage());
                }
            }
        }

    }

    /***   Metodo per Sirolfo   ***/
    /* //TODO metodo forse inutile perché le carte acquistate si aggiornano automaticamente al momento dell'acquisto
    public void repaint() {

        for (int i = 0; i < 4; i++) {
            TowerTileWidget[] towerActionSpaces = board.getTowerTileWidgetList(towerColorArray[i]);
            for (int j = 0; j < 4 )

        for (TowerTileWidget[] tower: towerActionSpaces) {
            for (TowerTileWidget actionSpace: tower) {
                if(actionSpace.getAssociatedCard().isTaken() && actionSpace.getOccupantPlayerColor() == board.getPlayer().getPlayerColor()) {
                    // enter if the card is taken with a familiar of the player
                    actionSpace.getAssociatedCard().addToPersonalBoard(this);
                }
            }
        }

    }
    */

   public HBox[] getCardHboxes() {
       return cardHboxes;
   }
}
