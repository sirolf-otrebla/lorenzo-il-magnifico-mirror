package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 04/07/17.
 */
public class OpponentBoardWindow extends PersonalBoardWindow{

    private LeaderPopup leaderPopup = new LeaderPopup();


    public OpponentBoardWindow(GUIMain board, String username, CardOnPersonalWidget[][] cardAcquiredWidget,
                               LeaderWidget[] leaderWidgets, BonusTileWidget bonusTile) {
        super(board, username, cardAcquiredWidget, leaderWidgets, bonusTile);
    }

    public void display() {

        Stage stage = new Stage();

        stage.setTitle(this.username + "'s personal board");
        stage.setResizable(false);
        stage.centerOnScreen();

        // calculate and set window dimensions
        PersonalBoardWindow.personalBoardHeight = stageHeight * 0.90;
        PersonalBoardWindow.personalBoardWidth = (stageWidth * 0.90 * ORIGINAL_RATIO);
        stage.setHeight(PersonalBoardWindow.personalBoardHeight);
        stage.setWidth(PersonalBoardWindow.personalBoardWidth);
        personalBoardResize = personalBoardWidth / ORIGINAL_WIDTH;

        final Pane pane = new Pane();
        pane.setId("opponentBoard");

        /* add button that shows bonus tile */
        Button showBonusTileButton = new Button("Bonus tile");
        showBonusTileButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showBonusTileButton.setOnAction((ActionEvent e) -> {
            //TODO aggiungere popup
            ; // bonus tile scelta
        });

        /* add button for leaders */
        Button showLeaderButton = new Button("Leader Played");
        showLeaderButton.setOnAction((ActionEvent e) -> {
            leaderPopup.display(leaderWidgets);
        });

        /* set buttons position on window */
        HBox hbox = new HBox(100 * resize);
        hbox.setLayoutX((72.0 / 100) * personalBoardWidth);
        hbox.setLayoutY((1.0 / 100) * personalBoardHeight);

        /*
        pane.minWidthProperty().bind(stage.widthProperty());
        pane.minHeightProperty().bind(stage.heightProperty());
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.maxWidthProperty().bind(stage.widthProperty());
        pane.maxHeightProperty().bind(stage.heightProperty());
        */

        /* show acquired cards */
        setCardAcquiredLayout();

        Scene personalScene = new Scene(pane);

        File f = new File("./src/main/res/fx-style.css");
        try {
            personalScene.getStylesheets().add(f.toURI().toURL().toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        stage.setScene(personalScene);
        stage.show();
    }

}

