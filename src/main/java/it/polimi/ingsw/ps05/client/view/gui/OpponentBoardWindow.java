package it.polimi.ingsw.ps05.client.view.gui;

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



    public OpponentBoardWindow(GUIMain board, String username, LeaderWidget[] leaderWidgets, ResourcesWidget resourcesWidget) {
        //super(board, username, cardAcquiredWidget, leaderWidgets, bonusTile);
        super(board);
        this.username = username;
        this.leaderWidgets = leaderWidgets;

    }

    @Override
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
            leaderPopup.display();
        });

        /* add close button */
        Button closeButton = new Button("Close");
        closeButton.layoutXProperty().bind(stage.widthProperty().multiply(90 / 100));
        closeButton.layoutYProperty().bind(stage.heightProperty().multiply(1 / 100));
        closeButton.setOnAction((ActionEvent e) -> {
            stage.close();
        });
        pane.getChildren().add(closeButton);

        /* set buttons position on window */
        HBox hbox = new HBox(50 * resize);
        hbox.setLayoutX((55.0 / 100) * personalBoardWidth);
        hbox.setLayoutY((1.0 / 100) * personalBoardHeight);
        hbox.getChildren().addAll(showLeaderButton, showBonusTileButton);
        pane.getChildren().add(hbox);

        /* show acquired cards */
        showCardAcquiredLayout();
        for(int i = 0; i < 4; i++) {
            pane.getChildren().add(cardHboxes[i]);
        }

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

    public void setResourceWidget(ResourcesWidget resourceWidget) {
        this.resourceWidget = resourceWidget;
    }

}

