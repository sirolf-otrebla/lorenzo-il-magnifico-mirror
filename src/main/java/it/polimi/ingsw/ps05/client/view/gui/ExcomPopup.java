package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by miotto on 29/06/17.
 */
public class ExcomPopup {

    public static void display() {

        Stage popup = new Stage();

        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL); // forces to deal with the popup before anything else
        popup.setMinWidth(250);

        Label label = new Label("Vatican Report");

        final ToggleGroup group = new ToggleGroup();

        RadioButton supportButton = new RadioButton("Support the Church");
        supportButton.setSelected(true);
        RadioButton sufferButton = new RadioButton("Suffer the excommunication");

        supportButton.setToggleGroup(group);
        sufferButton.setToggleGroup(group);

        HBox hbox = new HBox(20);
        hbox.setId("excomSelection");
        hbox.getChildren().addAll(supportButton, sufferButton);
        hbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hbox, 600, 300);
        popup.setScene(scene);
        popup.showAndWait();

    }

}
