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
    private static LeaderWidget[] leadersDrafted = new LeaderWidget[4];
    private static LeaderWidget[] leadersToDraftArray = new LeaderWidget[4];
    public static boolean draftCompleted;

    public static LeaderWidget[] display(LeaderWidget[] leadersToDraftArray) {

        LeaderDraftPopup.leadersToDraftArray = leadersToDraftArray;

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);


        HBox hbox = new HBox(30 * resize);
        hbox.setMinWidth(LEADER_HEIGHT_PERC * stageHeight);
        hbox.setFillHeight(true);

        // adding LeaderWidgets to hbox
        for(int i = 0; i < 4; i++) {
            setupGestureTarget(leadersToDraftArray[i]);
            hbox.getChildren().add(leadersToDraftArray[i].getLeaderCard());
        }

        Label label = new Label();
        label.setText("Choose the card you want to keep");

        VBox vbox = new VBox(20 * resize);
        vbox.setId("leaderDraftPopup");
        vbox.getChildren().addAll(label, hbox);

        Scene scene = new Scene(vbox);

        popup.setScene(scene);
        popup.setAlwaysOnTop(true);
        popup.showAndWait();

        draftCompleted = true;

        return leadersDrafted;

    }

    private static void setupGestureTarget(LeaderWidget leaderWidget) {

        // EVENTO CHE VA PASSATO AL CONTROLLER
        leaderWidget.getLeaderCard().setOnMouseClicked((MouseEvent e) -> {
            /* What to do when the leader is selected */
            //TODO capire come salvare i leader scelti (array, tree...)
            leaderWidget.getLeaderCard().setOpacity(0.7);
            leaderWidget.getLeaderCard().setMouseTransparent(true); // disable mouse click
            leaderWidget.setDrafted(true);
            leaderWidget.setOfPlayerProperty(true);
        });

    }

    private void repaint() {
        for(LeaderWidget leader: leadersToDraftArray) {
            leader.repaintDraft();
        }
    }
}
