package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 04/07/17.
 */
public class BonusTileDraftPopup {

    private static Stage popupStage;
    private static BonusTileWidget[] bonusTileArray = new BonusTileWidget[4];
    private static BonusTileWidget[] newBonusTileArray;
    private static boolean myTurn;
    private static BonusTileWidget selectedBonusTile;
    private static HBox hboxReference;

    public static void display() {

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        HBox hbox = new HBox(50 * resize);
        hboxReference = hbox;
        VBox vbox = new VBox(40 * resize);
        GridPane gridPane = new GridPane();

        // create bonusTile widgets
        for(int i = 0; i < 4; i++) {
            bonusTileArray[i] = new BonusTileWidget(i);
            bonusTileArray[i].setOpacity(0.3);
            System.out.println(bonusTileArray[i].getImagePath());
            hbox.getChildren().add(bonusTileArray[i]);
            //GridPane.setConstraints(bonusTileArray[i].getImage(), i, 0);
        }

        Label label = new Label("Choose your personal Bonus Tile");
        label.setId("labelTileDraft");

        //hbox.setFillHeight(true);
        //hbox.setPrefHeight((30 / 100) * stageHeight);


        vbox.setId("bonusTileDraft");
        vbox.getChildren().addAll(label, hbox);
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.setAlignment(Pos.CENTER);


        Scene scene = new Scene(vbox);

        // add stylesheets
        File f = new File("./src/main/res/fx-style.css");
        try {
            scene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        popupStage.centerOnScreen();
        popupStage.setScene(scene);
        //popupStage.sizeToScene();
        popupStage.showAndWait();

        // after the draft is completed, the selected bonus tile widget is returned to the caller
        for(BonusTileWidget bonusTile: bonusTileArray)
            if(bonusTile.isThisPlayerSelected())
                selectedBonusTile = bonusTile;

    }

    public static void updateDraft(Integer[] bonusTileToDraftArray) {

        int j = 0;
        for(int i = 0; i < 4; i++) {
            bonusTileArray[i].setDrafted(true);
            for(Integer bonusTileToDraft: bonusTileToDraftArray) {
                if (bonusTileArray[i].getReferenceId() == bonusTileToDraft) {
                    bonusTileArray[i].setMouseTransparent(false);
                    bonusTileArray[i].setOpacity(1);
                    bonusTileArray[i].setDrafted(false);
                    setupClickGesture(bonusTileArray[i]);
                }
            }
        }
    }

    /*
    private static void repaint() {

        for(BonusTileWidget bonusTile: bonusTileArray) {
            if(!bonusTile.isDrafted()) {
                // make already drafted tiles half-transparent
                bonusTile.setOpacity(0.3);
                // disable click for already drafted bonus tiles
                bonusTile.setMouseTransparent(true);
            }
        }
    }
    */

    private static void setupClickGesture(BonusTileWidget bonusTileToDraft) {

        bonusTileToDraft.setOnMouseEntered((MouseEvent e) -> {
            bonusTileToDraft.setCursor(Cursor.HAND);
        });

        bonusTileToDraft.setOnMouseClicked((MouseEvent e) -> {
            bonusTileToDraft.setDrafted(true);
            bonusTileToDraft.setThisPlayerSelected(true);
            //TODO comuicare al controller
        });

    }

    public static BonusTileWidget getSelectedBonusTile() {
        return selectedBonusTile;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }

    public static boolean isMyTurn() {
        return myTurn;
    }

    public static void setMyTurn(boolean myTurn) {
        BonusTileDraftPopup.myTurn = myTurn;
    }
}
