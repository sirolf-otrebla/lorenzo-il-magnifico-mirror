package it.polimi.ingsw.ps05.client.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageHeight;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageWidth;

/**
 * Created by miotto on 06/07/17.
 */
public class PrivilegePopup {

    public static final double PRIVILEGE_WIDTH_PERC = (22.0 / 100);
    private static final String path = "./src/main/res/img/privileges-table.png";
    private static Stage popupStage;
    static int alreadySelected;
    static ArrayList<Integer> answers = new ArrayList<>();

    public static ArrayList<Integer> display(final int conversionsToDo) {

        alreadySelected = 0;

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        final VBox privilegeConversionsButtonBox = new VBox(10 * resize);
        VBox vbox = new VBox(20 * resize);

        final ImageView conversionImage = new ImageView();
        File crDir = new File("./src/main/res/img/privilege-conversion.png");
        try {
            Image img = new Image(crDir.toURI().toURL().toString());
            conversionImage.setImage(img);
            conversionImage.setPreserveRatio(true);
            conversionImage.setFitWidth(PRIVILEGE_WIDTH_PERC * stageWidth);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        final Label introLabel = new Label("Convert your privilege");
        introLabel.setId("privilegeLabel");


        final Label conversionsLeftLabel = new Label("Conversions left: " + (conversionsToDo - alreadySelected));
        conversionsLeftLabel.setId("privilegeConversionsLabel");

        //hbox.setFillHeight(true);
        //hbox.setPrefHeight((30 / 100) * stageHeight);

        /* Setting up buttons for the conversions */ //TODO provare se i toggle button vanno bene per la scelta delle conversioni
        final ToggleGroup group = new ToggleGroup();

        final RadioButton woodStoneButton = new RadioButton("1 Wood & 1 Stone"); //TODO da rendere flessibile (prendere dal model le informazioni delle risorse)
        final RadioButton servantsButton = new RadioButton("2 Servants");
        final RadioButton goldButton = new RadioButton("2 Gold");
        final RadioButton militaryButton = new RadioButton("2 Military Points");
        final RadioButton faithButton = new RadioButton("1 Faith Point");

        woodStoneButton.setUserData(0);
        servantsButton.setUserData(1);
        goldButton.setUserData(2);
        militaryButton.setUserData(3);
        faithButton.setUserData(4);

        woodStoneButton.setToggleGroup(group);
        servantsButton.setToggleGroup(group);
        goldButton.setToggleGroup(group);
        militaryButton.setToggleGroup(group);
        faithButton.setToggleGroup(group);

        privilegeConversionsButtonBox.getChildren().addAll(woodStoneButton, servantsButton, goldButton, militaryButton, faithButton);


        // Listening to button click
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (group.getSelectedToggle() != null) {

                    if(conversionsToDo > alreadySelected + 1) {
                        // enter if ther is more than one conversion left
                        int selection = (int)group.getSelectedToggle().getUserData();
                        answers.add(selection);
                        alreadySelected++;
                        //System.out.println(answers[alreadySelected]);
                    } else if(conversionsToDo == alreadySelected + 1) {
                        // enter if it's the last conversion requested
                        int selection = (int)group.getSelectedToggle().getUserData();
                        answers.add(selection);
                        alreadySelected++;
                        //System.out.println(answers[alreadySelected]);
                        popupStage.close(); // close the window
                    }
                }
            }
        });

        /*
        Button confirmButton = new Button("OK");
        confirmButton.setOnAction((ActionEvent e) -> {
            popup.close();
        });
        */

        vbox.setId("privilegePopup");
        vbox.getChildren().addAll(introLabel, conversionImage, privilegeConversionsButtonBox, conversionsLeftLabel);
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);

        // add stylesheets
        File f = new File("./src/main/res/fx-style.css");
        try {
            scene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        popupStage.centerOnScreen();
        popupStage.setScene(scene);
        //popupStage.sizeToScene();
        popupStage.showAndWait();

        // after the selection is completed, the conversions are returned to the caller
        return answers;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }
}
