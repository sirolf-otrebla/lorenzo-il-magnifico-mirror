package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static it.polimi.ingsw.ps05.client.view.gui.CardOnBoardWidget.CARD_MIN_HEIGHT_BOARD;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class LeaderPopup {

    private static final double LEADERCARD_ZOOM = 2;

    public static void display(LeaderWidget[] personalLeaders) {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
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

        VBox vbox = new VBox(60 * resize);
        vbox.setId("personalLeaders");
        vbox.setAlignment(Pos.CENTER);

        addPersonalLeaders(personalLeaders, hbox1, hbox2);

        vbox.getChildren().addAll(label1, hbox1, label2, hbox2);

        Scene scene = new Scene(vbox);

        popup.setScene(scene);
        popup.show();

    }

    private static void addPersonalLeaders(LeaderWidget[] personalLeaders, HBox handBox, HBox playedBox) {
        handBox.getChildren().clear();
        playedBox.getChildren().clear();
        for(int i = 0; i < 4; i++) {
            if (!personalLeaders[i].isDiscarded())
                if (!personalLeaders[i].isPlayed())
                   // adds leaders to the box 1 if the card is still on hand
                   handBox.getChildren().add(personalLeaders[i].getLeaderCard());
                else
                    //adds leaders to the box 2 if the card has been played
                    playedBox.getChildren().add(personalLeaders[i].getLeaderCard());
        }
    }
}
