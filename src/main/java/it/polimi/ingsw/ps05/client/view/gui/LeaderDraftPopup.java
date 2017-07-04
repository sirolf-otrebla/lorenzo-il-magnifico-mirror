package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class LeaderDraftPopup {

    public static final double LEADER_HEIGHT_PERC = 30 / 100;

    private static Stage popup;
    static LeaderWidget[] leadersDrafted = new LeaderWidget[4];
    private static LeaderWidget[] leadersToDraftArray = new LeaderWidget[4];
    private static LeaderWidget[] newLeadersToDraftArray = new LeaderWidget[3];
    public static int numberOfLeadersAlreadySelected;
    public static boolean draftCompleted;

    public static void display(Integer[] referenceIdArray) {

        /* Initialize leaders to be drafted */
        int i = 0;
        for(Integer referenceId: referenceIdArray) {
            leadersToDraftArray[i] = new LeaderWidget(referenceId);
            i++;
        }

        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);

        HBox hbox = new HBox(30 * resize);
        hbox.setMinWidth(LEADER_HEIGHT_PERC * stageHeight);
        hbox.setFillHeight(true);

        // adding leaders to hbox
        for(LeaderWidget leader: leadersToDraftArray) {
            setupGestureTarget(leader);
            hbox.getChildren().add(leader.getLeaderCard());
        }

        Label label = new Label();
        label.setText("Choose one card you want to keep");

        VBox vbox = new VBox(20 * resize);
        vbox.setId("leaderDraftPopup");
        vbox.getChildren().addAll(label, hbox);

        Scene scene = new Scene(vbox);

        popup.setScene(scene);
        popup.setAlwaysOnTop(true);
        popup.showAndWait();

        draftCompleted = true;

    }

    private static void setupGestureTarget(LeaderWidget leaderWidget) {

        // EVENTO CHE VA PASSATO AL CONTROLLER

        leaderWidget.getLeaderCard().setOnMouseClicked((MouseEvent e) -> {
            /* What to do when the leader is selected */
            storeSelectedLeader(leaderWidget, numberOfLeadersAlreadySelected);
            leaderWidget.getLeaderCard().setOpacity(0.5);
            leaderWidget.getLeaderCard().setMouseTransparent(true); // disable mouse click
            leaderWidget.setDrafted(true);
            numberOfLeadersAlreadySelected++;
        });

    }

    //// DA CHIAMARE DALL'ESTERNO ////
    private void repaint(Integer[] newReferenceIdArray) {

        /* Insert new leaders in a new array */
        int i = 0;
        for(Integer referenceId: newReferenceIdArray) {
            newLeadersToDraftArray[i] = new LeaderWidget(referenceId);
            i++;
        }

        /* Updates the leaders to be drafted */
        i = 0;
        for(LeaderWidget leader: leadersToDraftArray) {
            try {
                if (!leader.isDrafted()) {
                    leader = newLeadersToDraftArray[i];
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    //// DA CHIAMARE DALL'ESTERNO ////
    private static void endLeaderDraft() {
        popup.close();
    }

    private static void storeSelectedLeader(LeaderWidget leaderToStore, int leadersAlreadySelected) {
        leadersDrafted[leadersAlreadySelected] = leaderToStore;
    }
}
