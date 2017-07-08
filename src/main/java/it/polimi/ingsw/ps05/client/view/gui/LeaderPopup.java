package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.TowerCardWidget.CARD_MIN_HEIGHT_BOARD;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class LeaderPopup {

    private static final double LEADERCARD_ZOOM = 1.3;
    private LeaderWidget[] personalLeaders;

    public void display() {

        Stage popup = new Stage();
        //popup.initModality(Modality.APPLICATION_MODAL);
        popup.centerOnScreen();

        Label label1 = new Label();
        label1.setText("In hand");
        Label label2 = new Label();
        label2.setText("Played");

        HBox hbox1 = new HBox(20 * resize);
        hbox1.setId("hand");
        hbox1.setPrefHeight(CARD_MIN_HEIGHT_BOARD * LEADERCARD_ZOOM * resize);
        hbox1.setFillHeight(true);
        hbox1.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox(20 * resize);
        hbox2.setId("played");
        hbox2.setPrefHeight(CARD_MIN_HEIGHT_BOARD * LEADERCARD_ZOOM * resize);
        hbox2.setFillHeight(true);
        hbox2.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20 * resize);
        vbox.setId("personalLeaders");
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.setAlignment(Pos.CENTER);

        addPersonalLeaders(personalLeaders, hbox1, hbox2);

        vbox.getChildren().addAll(label1, hbox1, label2, hbox2);

        Scene scene = new Scene(vbox);

        File f = new File("./src/main/res/fx-style.css");
        try {
            scene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        popup.setScene(scene);
        popup.show();

    }

    private void addPersonalLeaders(LeaderWidget[] personalLeaders, HBox handBox, HBox playedBox) {

        if(personalLeaders == null)
            return;

        // start from empty boxes
        handBox.getChildren().clear();
        playedBox.getChildren().clear();

        // fill the boxes
        for(int i = 0; i < 4; i++) {
            if (!personalLeaders[i].isDiscarded())
                if (!personalLeaders[i].isPlayed()) {

                    // add leaders to the box 1 if the card is still on hand
                    handBox.getChildren().add(personalLeaders[i]);
                    LeaderWidget leader = personalLeaders[i];
                    if(personalLeaders[i].isActivable()) {

                        // add click gesture if the card is activable
                        personalLeaders[i].setOnMouseEntered((MouseEvent e) -> {
                            leader.setCursor(Cursor.HAND);
                        });

                        personalLeaders[i].setOnMouseClicked((MouseEvent e) -> {
                            //TODO notificare il controller
                            if(LeaderActivationPopup.display()) {
                                // enter if player confirms activation
                                handBox.getChildren().remove(leader); //TODO controllare se funziona
                                playedBox.getChildren().add(leader);
                                leader.setMouseTransparent(true);
                            }
                        });
                    }
                } else {
                    // add leaders to the box 2 if the card has been played
                    playedBox.getChildren().add(personalLeaders[i]);
                    personalLeaders[i].setMouseTransparent(true);
                }
        }
    }

    public void setPersonalLeaders(LeaderWidget[] personalLeaders) {
        this.personalLeaders = personalLeaders;
    }
}
