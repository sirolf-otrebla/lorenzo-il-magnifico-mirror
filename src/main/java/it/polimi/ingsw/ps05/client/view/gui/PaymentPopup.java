package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by miotto on 29/06/17.
 */
public class PaymentPopup {


    static boolean canceled; // usato per comunicare che il giocatore ha annullato l'azione
    public Integer result;

    public int display(String cardName) {

        Stage popup = new Stage();

        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL); // forces to deal with the popup before anything else
        //popup.setMinWidth(250);

        Label label = new Label("Choose payment method for " + cardName);

        ToggleGroup toggleGroup = new ToggleGroup();

        VBox selectablesBox = new VBox();
        ArrayList<RadioButton> buttonList = new ArrayList<RadioButton>();

        int i = 0;

        RadioButton rb1 = new RadioButton("First payment");
        buttonList.add(rb1);
        rb1.setToggleGroup(toggleGroup);
        rb1.setUserData(0);
        selectablesBox.getChildren().add(rb1);

        RadioButton rb2 = new RadioButton("Second payment");
        buttonList.add(rb2);
        rb2.setToggleGroup(toggleGroup);
        rb2.setUserData(1);
        selectablesBox.getChildren().add(rb2);

        Button confirmButton = new Button("OK");
        confirmButton.setOnAction((ActionEvent e) -> {
            popup.close();
        });

        /* Adding cancel button */
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((ActionEvent e) -> {
            canceled = true;
            popup.close();
        });

        HBox utilButtonBox = new HBox(40 * resize);
        utilButtonBox.getChildren().addAll(confirmButton, cancelButton);

        VBox vbox = new VBox(20 * resize);
        vbox.setId("paymentSelection");
        vbox.setPadding(new Insets(50 * resize, 50 * resize, 50 * resize, 50 * resize));
        vbox.getChildren().addAll(label, selectablesBox, utilButtonBox);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 600 * resize, 300 * resize);

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

        if(canceled)
            return -1;
        else {
            result = (int)toggleGroup.getSelectedToggle().getUserData();
            return (int)toggleGroup.getSelectedToggle().getUserData(); // ritorna
        }


    }



}








/* Creating buttons for the selection */

//TODO: in base a parametri da aggiungere i pulsanti avranno l'immagine della risorsa 'resimage' come sfondo e il valore 'resValue' come testo
/*
        Button firstAltButton = new Button();
        Button secondAltButton = new Button();

        HBox hbox = new HBox(40 * resize);
        hbox.getChildren().addAll(firstAltButton, secondAltButton);
        */

        /*
        firstAltButton.setStyle("-fx-background-image: " + resImage1);
        firstAltButton.setText(resValue1.toString());
        firstAltButton.setOnAction((ActionEvent e) -> {
            //TODO: comunicare la scelta al controller
        });

        secondAltButton.setStyle("-fx-background-image: " + resImage2);
        secondAltButton.setText(resValue2.toString());
        secondAltButton.setOnAction((ActionEvent e) -> {
            //TODO: comunicare la scelta al controller
        });



        /* Setting layout for the payment selection buttons */