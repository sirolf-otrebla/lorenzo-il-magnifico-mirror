package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 04/07/17.
 */
public class BonusTileDraftPopup {

    private static final HBox hbox = new HBox(20 * resize);;
    private static BonusTileWidget[] bonusTileArray = new BonusTileWidget[4];
    private static Stage popupStage;
    private static BonusTileWidget selectedBonusTile;

    public static void display() {

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        // create bonusTile widgets
        int i = 0;
        for(BonusTileWidget bonusTile: bonusTileArray) {
            bonusTile = new BonusTileWidget(i); //TODO controllare se va bene istanziare le bonus tile con questi referenceId (da 0 a 3)
            hbox.getChildren().add(bonusTile);
        }
        Label label = new Label("Choose your personal Bonus Tile");

        hbox.setFillHeight(true);
        hbox.setPrefHeight((50 / 100) * stageHeight);

        VBox vbox = new VBox(20 * resize);
        vbox.getChildren().addAll(label, vbox);

        Scene scene = new Scene(vbox);

        popupStage.setScene(scene);
        popupStage.sizeToScene();
        popupStage.showAndWait();

        // after the draft is completed, the selected bonus tile widget is returned to the caller
        for(BonusTileWidget bonusTile: bonusTileArray)
            if(bonusTile.isThisPlayerSelected())
                selectedBonusTile = bonusTile;

    }

    public void repaint() {

        //TODO implementare il repaint nella classe della finestra o nella bonusTile stessa?
        for(BonusTileWidget bonusTile: bonusTileArray) {
            if(!bonusTile.isDrafted()) {
                // make already drafted tiles half-transparent
                bonusTile.setOpacity(0.5);
                // disable click for already drafted bonus tiles
                bonusTile.setMouseTransparent(true);
            }
        }
    }

    public static BonusTileWidget getSelectedBonusTile() {
        return selectedBonusTile;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }
}