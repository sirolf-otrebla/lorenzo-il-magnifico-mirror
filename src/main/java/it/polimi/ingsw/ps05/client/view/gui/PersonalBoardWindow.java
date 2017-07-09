package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class PersonalBoardWindow {

    public static final double ORIGINAL_WIDTH = 3459, ORIGINAL_HEIGHT = 3800;
    public static double personalBoardWidth, personalBoardHeight, personalBoardResize, ORIGINAL_RATIO = (ORIGINAL_WIDTH / ORIGINAL_HEIGHT);

    GUIMain board;
    private HashMap<ColorEnumeration, ArrayList<AcquiredCardWidget>> cardAcquiredColorMap = new HashMap<>();
    private BonusTileWidget bonusTile;

    public static final ColorEnumeration[] acquiredCardColorArray = {
            ColorEnumeration.Yellow,
            ColorEnumeration.Green,
            ColorEnumeration.Violet,
            ColorEnumeration.Blue
    };

    HashMap<ColorEnumeration, HBox> cardHboxesMap;


    public PersonalBoardWindow(GUIMain board) {

        // calculate and set window dimensions
        personalBoardHeight = stageHeight * 0.90;
        personalBoardWidth = (stageHeight * 0.90) * ORIGINAL_RATIO;
        personalBoardResize = personalBoardWidth / ORIGINAL_WIDTH;
        System.out.println("personal width" + personalBoardWidth);
        System.out.println("personal height" + personalBoardHeight);
        System.out.println("original ratio" + ORIGINAL_RATIO);
        System.out.println("stagewidth" + stageWidth);
        System.out.println("stageheight" + stageHeight);

        this.board = board;

        // popolo la hashmap delle HBox contenenti le carte
        for(ColorEnumeration cardColor: acquiredCardColorArray) {
            cardHboxesMap.put(cardColor, new HBox(10));
        }

        // popolo la hashmap deglle liste di carte
        for(ColorEnumeration cardColor: acquiredCardColorArray) {
            cardAcquiredColorMap.put(cardColor, new ArrayList<AcquiredCardWidget>());
        }

        // configuro layout delle HBox
        int i = 0;
        for(ColorEnumeration cardColor: acquiredCardColorArray) {
            cardHboxesMap.get(cardColor).setLayoutX((2.4277 / 100) * personalBoardWidth);
            cardHboxesMap.get(cardColor).setLayoutY((1.8684 + 25.5 * i) / 100 * personalBoardHeight);
            cardHboxesMap.get(cardColor).setPrefHeight((21.0 / 100) * personalBoardHeight);
            cardHboxesMap.get(cardColor).setPrefWidth((90.0 / 100) * personalBoardWidth);
            cardHboxesMap.get(cardColor).setFillHeight(true);
        }
    }

    public void display() {

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("My Board");
        stage.setResizable(false);

        stage.setHeight(personalBoardHeight);
        stage.setWidth(personalBoardWidth);

        final Pane pane = new Pane();
        pane.setId("personalBoard");

        /* Add button for bonus tile */
        Button showBonusTileButton = new Button("Bonus tile");
        // showBonusTileButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showBonusTileButton.setOnAction((ActionEvent e) -> {
            displayBonusTile();
        });
        showBonusTileButton.setLayoutX((55.0 / 100) * personalBoardWidth);
        showBonusTileButton.setLayoutY((1.0 / 100) * personalBoardHeight);

        /* Add Close button */
        Button closeButton = new Button("Close");
        showBonusTileButton.setLayoutX((85.0 / 100) * personalBoardWidth);
        showBonusTileButton.setLayoutY((1.0 / 100) * personalBoardHeight);
        closeButton.setOnAction((ActionEvent e) -> {
            stage.close();
        });

        pane.getChildren().addAll(showBonusTileButton, closeButton);

        /* show acquired cards */
        for(ColorEnumeration cardColor: acquiredCardColorArray)
            pane.getChildren().add(cardHboxesMap.get(cardColor));


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


    void displayBonusTile() {
        Stage stage = new Stage();

        VBox vbox = new VBox();
        vbox.getChildren().add(getBonusTileWidget());

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public BonusTileWidget getBonusTileWidget() {
        return bonusTile;
   }

   public void setBonusTileWidget(BonusTileWidget bonusTileWidget) {
        this.bonusTile = bonusTileWidget;
    }

    public HashMap<ColorEnumeration, HBox> getCardHboxesMap() {
        return cardHboxesMap;
    }

    public ArrayList<AcquiredCardWidget> getProductionCards () {
       return this.cardAcquiredColorMap.get(ColorEnumeration.Yellow);
    }

    public ArrayList<AcquiredCardWidget> getHarvestingCards () {
        return this.cardAcquiredColorMap.get(ColorEnumeration.Green);
    }
}












 /*
    void showCardAcquiredLayout() {

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
                for(int k = 0; k < 6; k++) {
                    // inside the k card of j color
                    if(cardAcquiredWidget[j][k].getImage() != null)
                        cardHboxes[i].getChildren().add(cardAcquiredWidget[j][k]);
                }
            }
        }

    }
    */

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
