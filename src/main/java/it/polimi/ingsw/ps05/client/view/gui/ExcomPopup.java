package it.polimi.ingsw.ps05.client.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 29/06/17.
 */
public class ExcomPopup {


    static boolean answerSelected = false;
    static boolean answer = true;

    // il metodo display ritorna 'answer' quando il popup viene chiuso
    public static boolean display() {

        Stage popup = new Stage();

        // double popupHeight = GUIMain.stageHeight * (30 / 100);

        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL); // forces to deal with the popup before anything else

        Label label = new Label("Vatican Report");

        // Buttons for the support/suffer selection
        final ToggleGroup group = new ToggleGroup();

        RadioButton supportButton = new RadioButton("Support the Church");
        supportButton.setUserData(true);
        RadioButton sufferButton = new RadioButton("Suffer the excommunication");
        sufferButton.setUserData(false);

        supportButton.setSelected(true);

        supportButton.setToggleGroup(group);
        sufferButton.setToggleGroup(group);

        // Listening to button click
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (group.getSelectedToggle() != null) {

                    answer = (boolean)group.getSelectedToggle().getUserData();
                    System.out.println(ExcomPopup.answer);

                }

            }
        });

        VBox selectionButtons = new VBox(20 * resize);
        selectionButtons.setId("excomSelection");
        selectionButtons.getChildren().addAll(supportButton, sufferButton);
        selectionButtons.setAlignment(Pos.CENTER);

        Button closeButton = new Button("Confirm");
        closeButton.setOnAction((ActionEvent e) -> {
            if(answerSelected)
                popup.close();
            else {}
        });

        VBox vbox = new VBox(30 * resize);
        vbox.setId("excomPopup");
        vbox.getChildren().addAll(label, selectionButtons, closeButton);

        Scene scene = new Scene(vbox, 600 * resize, 300 * resize);
        popup.setScene(scene);
        popup.showAndWait();

        return answer;

    }

}
