package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by miotto on 02/07/17.
 */
public class PersonalBoardWindow {

    public static final int ORIGINAL_WIDTH = 3600, ORIGINAL_HEIGHT = 3800, ORIGINAL_RATIO = ORIGINAL_WIDTH / ORIGINAL_HEIGHT;

    private HBox personalCardBoxes[] = new HBox[4];
    private static double stageWidth, stageHeight;
    private GUIMain board;

    public void display(Rectangle2D screenBounds) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Personal Board");
        stage.setResizable(false);
        stage.setMinHeight(250); //TODO impostare la grandezza minima corretta

        double screenRatio = screenBounds.getWidth() / screenBounds.getHeight();

        if(screenRatio <= ORIGINAL_RATIO) {
            PersonalBoardWindow.stageWidth = screenBounds.getWidth();
            PersonalBoardWindow.stageHeight = screenBounds.getWidth() / ORIGINAL_RATIO;
            stage.setWidth(PersonalBoardWindow.stageWidth);
            stage.setHeight(PersonalBoardWindow.stageHeight);
        } else {
            PersonalBoardWindow.stageHeight = screenBounds.getHeight();
            PersonalBoardWindow.stageWidth = screenBounds.getHeight() * ORIGINAL_RATIO;
            stage.setHeight(PersonalBoardWindow.stageHeight);
            stage.setWidth(PersonalBoardWindow.stageWidth);
        }

        final Pane pane = new Pane();
        pane.setId("personalBoard");

        pane.minWidthProperty().bind(stage.widthProperty());
        pane.minHeightProperty().bind(stage.heightProperty());
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.maxWidthProperty().bind(stage.widthProperty());
        pane.maxHeightProperty().bind(stage.heightProperty());

        /*
        for (int i = 0; i < 4; i++) {
            this.personalCardBoxes[i] = new HBox(20); //TODO controllare che lo spacing sia corretto
            this.personalCardBoxes[i].setLayoutX(2.4277 * );
        }
        */



        Scene personalScene = new Scene(pane);

        //scene.getStylesheets().add("./src/main/res/fx-style.css");
    }


    public void repaint() {
        TowerTileWidget[][] towerActionSpaces = board.getTowerTileWidgetList();

        /*
        for (TowerTileWidget[] tower: towerActionSpaces) {
            for (TowerTileWidget actionSpace: tower) {
                if(actionSpace.getAssociatedCard().isTaken())
            }
        }
        */
    }

}
