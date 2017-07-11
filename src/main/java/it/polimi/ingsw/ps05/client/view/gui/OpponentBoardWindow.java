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

    private String username;
    private LeaderWidget[] leaderWidgets = new LeaderWidget[4];
    private LeaderPopup leaderPopup = new LeaderPopup();
    private ResourcesWidget resourceWidget = new ResourcesWidget();

    public OpponentBoardWindow(GUIMain board, String username, LeaderWidget[] leaderWidgets) {
        super(board);
        this.username = username;
        this.leaderWidgets = leaderWidgets;
    }

    public void display() {

        Stage stage = new Stage();

        stage.setTitle(this.username + "'s personal board");
        stage.setResizable(false);
        stage.centerOnScreen();

        final Pane pane = new Pane();
        pane.setId("opponentBoard");

        /* add button that shows bonus tile */
        Button showBonusTileButton = new Button("Bonus tile");
        showBonusTileButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showBonusTileButton.setOnAction((ActionEvent e) -> {
            //TODO aggiungere popup
            displayBonusTile();
        });

        /* add button for leaders */
        Button showLeaderButton = new Button("Leader");
        showLeaderButton.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showLeaderButton.setOnAction((ActionEvent e) -> {
            leaderPopup.display();
        });

        /* button for resources */
        Button showResources = new Button("Resources");
        showResources.setPrefSize((3 / 100) * personalBoardWidth, (1 / 100) * personalBoardHeight);
        showResources.setOnAction((ActionEvent e) -> {
            this.resourceWidget.display();
        });

        /* add close button */
        Button closeButton = new Button("Close");
        showBonusTileButton.setLayoutX((85.0 / 100) * personalBoardWidth);
        showBonusTileButton.setLayoutY((1.0 / 100) * personalBoardHeight);
        closeButton.setOnAction((ActionEvent e) -> {
            stage.close();
        });

        /* set buttons position on window */
        HBox hbox = new HBox(50 * resize);
        hbox.setLayoutX((55.0 / 100) * personalBoardWidth);
        hbox.setLayoutY((1.0 / 100) * personalBoardHeight);
        hbox.getChildren().addAll(showResources, showLeaderButton, showBonusTileButton);
        pane.getChildren().addAll(hbox, closeButton);

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

    public LeaderWidget[] getLeaderWidgets() {
        return leaderWidgets;
    }

    public ResourcesWidget getResourceWidget() {
        return resourceWidget;
    }
}

