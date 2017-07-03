package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

/**
 * Created by miotto on 02/07/17.
 */
public class PersonalBoardWindow {

    public static final int ORIGINAL_WIDTH = 3459, ORIGINAL_HEIGHT = 3800, ORIGINAL_RATIO = ORIGINAL_WIDTH / ORIGINAL_HEIGHT;

    private ColorEnumeration playerColor = ColorEnumeration.Red;
    private HBox personalCardBoxes[] = new HBox[4];
    public static double personalBoardWidth, personalBoardHeight, personalBoardResize;
    private GUIMain boardWindow;

    public PersonalBoardWindow(GUIMain board) {
        this.boardWindow = board;

    }

    public void display(double mainBoardHeight) {
        Stage stage = new Stage();

        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Personal Board");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setMinHeight(250); //TODO impostare la grandezza minima corretta

        PersonalBoardWindow.personalBoardHeight = mainBoardHeight - 50; //TODO trasformare il -50 in percentuale
        PersonalBoardWindow.personalBoardWidth = (mainBoardHeight - 50) * ORIGINAL_RATIO;
        stage.setHeight(PersonalBoardWindow.personalBoardHeight);
        stage.setWidth(PersonalBoardWindow.personalBoardWidth);

        personalBoardResize = personalBoardWidth / ORIGINAL_WIDTH;

        final Pane pane = new Pane();
        pane.setId("personalBoard");


        pane.minWidthProperty().bind(stage.widthProperty());
        pane.minHeightProperty().bind(stage.heightProperty());
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.maxWidthProperty().bind(stage.widthProperty());
        pane.maxHeightProperty().bind(stage.heightProperty());


        for (int i = 0; i < 4; i++) {
            this.personalCardBoxes[i] = new HBox(2); //TODO controllare che lo spacing sia corretto
            this.personalCardBoxes[i].setLayoutX((2.4277 / 100) * PersonalBoardWindow.personalBoardWidth);
            this.personalCardBoxes[i].setLayoutY((1.8684 + 23.0*i) / 100 * PersonalBoardWindow.personalBoardHeight); // 23.0 distanza verticale tra gruppi di carte

            this.personalCardBoxes[i].setPrefHeight((21.0 / 100) * personalBoardHeight);
            this.personalCardBoxes[i].setPrefWidth((90.0 / 100) * personalBoardWidth);
            /*
            this.personalCardBoxes[i].setMinHeight(personalBoardHeight);
            this.personalCardBoxes[i].setMinWidth(personalBoardWidth);
            this.personalCardBoxes[i].setPrefHeight(personalBoardHeight);
            this.personalCardBoxes[i].setPrefWidth(personalBoardWidth);
            this.personalCardBoxes[i].setMaxHeight(personalBoardHeight);
            this.personalCardBoxes[i].setMaxWidth(personalBoardWidth);
            */

            this.personalCardBoxes[i].setFillHeight(true);
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


    public void repaint() {

        TowerTileWidget[][] towerActionSpaces = boardWindow.getTowerTileWidgetList();

        for (TowerTileWidget[] tower: towerActionSpaces) {
            for (TowerTileWidget actionSpace: tower) {
                if(actionSpace.getAssociatedCard().isTaken() && actionSpace.getOccupantPlayerId() == boardWindow.getThisPlayerColor()) {
                    // enter if the card is taken with a familiar of the player
                    actionSpace.getAssociatedCard().addToPersonalBoard(this);
                }
            }
        }

    }

    public ColorEnumeration getPlayerColor() {
        return playerColor;
    }

    public HBox[] getPersonalCardBoxes() {
        return personalCardBoxes;
    }
}
