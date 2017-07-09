package it.polimi.ingsw.ps05.client.view.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 07/07/17.
 */
public class HarvestingPopup {

    // il popup salva le scelte del giocatore nella lista 'selected'

    static final int CARD_HEIGHT = 220;

    private HBox cardsToActivateBox;
    private ArrayList<AcquiredCardWidget> cardsToActivate;
    private ArrayList<Integer> selected; //TODO passare l'array al controller

    public ArrayList<Integer> display(ArrayList<AcquiredCardWidget> cardsToActivate) {

        this.cardsToActivate = cardsToActivate;

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);
        popup.centerOnScreen();

        Label label1 = new Label();
        label1.setText("Choose the cards you want to activate");

        cardsToActivateBox = new HBox(20 * resize);
        cardsToActivateBox.setId("cardsToActivateBox");
        cardsToActivateBox.setPrefHeight(CARD_HEIGHT * resize);
        cardsToActivateBox.setMaxHeight(CARD_HEIGHT * resize);
        cardsToActivateBox.setFillHeight(true);
        cardsToActivateBox.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("OK");
        confirmButton.setOnAction((ActionEvent e) -> {
            popup.close();
        });

        VBox vbox = new VBox(60 * resize);
        vbox.setId("harvestingPopup");
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.setAlignment(Pos.CENTER);

        showCards();

        vbox.getChildren().addAll(label1, cardsToActivateBox, confirmButton);

        Scene scene = new Scene(vbox);

        File f = new File("./src/main/res/fx-style.css");
        try {
            scene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        popup.setScene(scene);
        popup.showAndWait();

        return selected;

    }

    private void showCards() {
        cardsToActivateBox.getChildren().clear();
        for(AcquiredCardWidget card: cardsToActivate) {
            cardsToActivateBox.getChildren().add(card);
            card.setOnMouseClicked((MouseEvent e) -> {
                selected.add(card.getReferenceId());
                card.setOpacity(0.7);
                card.setMouseTransparent(true);
            });
        }
    }


    public HBox getCardsToActivateBox() {
        return cardsToActivateBox;
    }

    public ArrayList<AcquiredCardWidget> getCardsToActivate() {
        return cardsToActivate;
    }
}
