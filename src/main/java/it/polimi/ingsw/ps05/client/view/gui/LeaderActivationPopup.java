package it.polimi.ingsw.ps05.client.view.gui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 03/07/17.
 */
public class LeaderActivationPopup {

    static boolean answer;

    public static boolean display() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(20 * resize);

        Label label = new Label("Activate this leader");

        Button confirmButton = new Button("Confirm");
        confirmButton.setDefaultButton(true);
        confirmButton.setOnAction((ActionEvent e) -> {
            answer = true;
            popup.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((ActionEvent e) -> {
            answer = false;
            popup.close();
        });
        HBox hbox = new HBox(20 * resize);
        hbox.getChildren().addAll(confirmButton, cancelButton);

        vbox.getChildren().add(hbox);

        Scene scene = new Scene(vbox);

        popup.setScene(scene);
        popup.showAndWait();

        return answer;
    }
}
