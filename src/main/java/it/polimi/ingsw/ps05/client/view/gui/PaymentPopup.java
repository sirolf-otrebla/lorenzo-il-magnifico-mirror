package it.polimi.ingsw.ps05.client.view.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

/**
 * Created by miotto on 29/06/17.
 */
public class PaymentPopup {

    ArrayList<Integer> resIdArrayList = new ArrayList<>();

    static boolean answer = true; // usato per comunicare che il giocatore ha annullato l'azione

    /* TODO aggiungere parametri al metodo display per visualizzare le immagini delle risorse durante la scelta */
    public static boolean display(String cardName) {

        Stage popup = new Stage();

        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.APPLICATION_MODAL); // forces to deal with the popup before anything else
        popup.setMinWidth(250);

        Label label = new Label("Choose payment method for " + cardName);

        /* Creating buttons for the selection */
        Button firstAltButton = new Button();
        Button secondAltButton = new Button();

        //TODO: in base a parametri da aggiungere i pulsanti avranno l'immagine della risorsa 'resimage' come sfondo e il valore 'resValue' come testo

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
        */

        /* Setting layout for the payment selection buttons */
        HBox hbox = new HBox(40);
        hbox.getChildren().addAll(firstAltButton, secondAltButton);

        /* Adding cancel button */
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((ActionEvent e) -> {
            answer = false;
            popup.close();
        });

        VBox vbox = new VBox(20);
        vbox.setId("paymentSelection");
        vbox.getChildren().addAll(hbox, cancelButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 600, 300);
        popup.setScene(scene);
        popup.showAndWait();

        return answer;
    }

    public void setResIdArrayList(ArrayList<Integer> resIdArrayList) {
        this.resIdArrayList = resIdArrayList;
    }
}