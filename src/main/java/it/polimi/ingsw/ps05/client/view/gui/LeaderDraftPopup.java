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
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class LeaderDraftPopup {

    private static Stage popup;
    static LeaderWidget[] leadersDrafted = new LeaderWidget[4];
    private static LeaderWidget[] leadersToDraftArray = new LeaderWidget[4];
    private static LeaderWidget[] newLeadersToDraftArray = new LeaderWidget[3];
    public static int numberOfLeadersAlreadySelected;
    public static boolean draftCompleted;
    private static HBox hboxReference;

    public static void display(Integer[] referenceIdArray) {

        /* Initialize leaders to be drafted */

        for(int i = 0; i < 4; i++) {
            leadersToDraftArray[i] = new LeaderWidget(referenceIdArray[i]);
        }

        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);

        HBox hbox = new HBox(30 * resize);
        hboxReference = hbox;
        //hbox.setPrefWidth(LEADER_HEIGHT_PERC * stageHeight);
        //hbox.setFillHeight(true);

        // adding initial leaders to hbox
        for(LeaderWidget leader: leadersToDraftArray) {
            setupGestureTarget(leader);
            hbox.getChildren().add(leader);
        }

        Label label = new Label();
        label.setText("Choose one card you want to keep");
        label.setId("leaderDraftLabel");

        VBox vbox = new VBox(40 * resize);
        vbox.setId("leaderDraftPopup");
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, hbox);

        Scene scene = new Scene(vbox);

        // add stylesheets
        File f = new File("./src/main/res/fx-style.css");
        try {
            scene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        popup.setScene(scene);
        popup.setAlwaysOnTop(true);
        popup.showAndWait();

        draftCompleted = true;

    }

    private static void setupGestureTarget(LeaderWidget leaderWidget) {

        // EVENTO CHE VA PASSATO AL CONTROLLER

        if(!leaderWidget.isDrafted()) {
            leaderWidget.setOnMouseEntered((MouseEvent e) -> {
                /* What to do when the mouse is over the leader card */
                leaderWidget.setCursor(Cursor.HAND);
            });

            leaderWidget.setOnMouseClicked((MouseEvent e) -> {
                /* What to do when the leader is selected */
                storeSelectedLeader(leaderWidget, numberOfLeadersAlreadySelected);
                leaderWidget.setOpacity(0.2);
                leaderWidget.setMouseTransparent(true); // disable mouse click
                leaderWidget.setDrafted(true);
                numberOfLeadersAlreadySelected++;
            });
        } else {
            leaderWidget.setMouseTransparent(true);
        }

    }

    //// DA CHIAMARE DALL'ESTERNO ////
    public static void updateLeadersToDraft(Integer[] newReferenceIdArray) {

        /* Insert new leaders in a new array */
        int leadersRemained = 4 - numberOfLeadersAlreadySelected; //leaders left to choose
        for (int i = 0; i < leadersRemained; i++)
            newLeadersToDraftArray[i] = new LeaderWidget(newReferenceIdArray[i]);

        /* Insert the new leaders in the leadersToDraft array */
        for (int i = 0; i < 4; i++) {
            int j = 0;
            if (!leadersToDraftArray[i].isDrafted()) {
                leadersToDraftArray[i] = newLeadersToDraftArray[j];
                j++;
            }
        }

        repaint();

    }

    private static void repaint() {

        hboxReference.getChildren().clear();

        for(LeaderWidget leader: leadersToDraftArray) {
            setupGestureTarget(leader);
            hboxReference.getChildren().add(leader);
        }

    }

    //// DA CHIAMARE DALL'ESTERNO ////
    public static void endLeaderDraft() {
        popup.close();
    }

    /* Updates the leader drafted array */
    private static void storeSelectedLeader(LeaderWidget leaderToStore, int numberOfLeadersAlreadySelected) {
        leadersDrafted[numberOfLeadersAlreadySelected] = leaderToStore;
    }

    public static Stage getPopup() {
        return popup;
    }
}
