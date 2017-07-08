package it.polimi.ingsw.ps05.client.view.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 07/07/17.
 */
public class ActivationAlternativesPopup {

    public static int display() {

        Stage popup = new Stage();

        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL); // forces to deal with the popup before anything else
        //popup.setMinWidth(250);

        Label label = new Label("This cards offers multiple conversions, choose one");

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton rb1 = new RadioButton("First");
        rb1.setToggleGroup(toggleGroup);
        rb1.setUserData(1);

        RadioButton rb2 = new RadioButton("Second");
        rb2.setToggleGroup(toggleGroup);
        rb2.setUserData(2);

        Button confirmButton = new Button("OK");
        confirmButton.setOnAction((ActionEvent e) -> {
            popup.close();
        });

        VBox vbox = new VBox(20);
        vbox.setId("paymentSelection");
        vbox.setPadding(new Insets(30 * resize, 30 * resize, 30 * resize, 30 * resize));
        vbox.getChildren().addAll(label, rb1, rb2);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        popup.setScene(scene);
        popup.showAndWait();

        return (int)toggleGroup.getSelectedToggle().getUserData();
    }
}
