package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageHeight;

/**
 * Created by miotto on 08/07/17.
 */
public class EndGamePopup {

    private static final double ICON_HEIGHT_PERC = (20 / 100), MARKER_RADIUS = 17;

    private static Stage popupStage;

    public static void display(ArrayList<Pair<ColorEnumeration, Integer>> finalPointsList) {

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        VBox vbox = new VBox(40 * resize);

        Label introLabel = new Label("The game is finished!");
        introLabel.setId("introLabelEndGame");
        introLabel.setStyle("-fx-font-size: 35");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20 * resize);

        int i = 0;
        // create final victory points
        for(Pair<ColorEnumeration, Integer> playerFinalPoints: finalPointsList) {

            // show final victory points
            File crDir = new File("./src/main/res/img/victory-icn.png");
            ImageView victoryIcon = new ImageView();
            try {
                Image img = new Image(crDir.toURI().toURL().toString());
                victoryIcon.setImage(img);
                victoryIcon.setPreserveRatio(true);
                victoryIcon.setFitHeight(ICON_HEIGHT_PERC * stageHeight);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            Label playerVictoryPointsLabel = new Label(playerFinalPoints.getValue().toString());

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(victoryIcon, playerVictoryPointsLabel);
            stackPane.setAlignment(Pos.CENTER);

            gridPane.add(stackPane, i, 0);

            String markerPath = GraphicResources.getMarkerPath(playerFinalPoints.getKey());
            File markerFile = new File(markerPath);

            // show markers
            try {
                Image img = new Image(markerFile.toURI().toURL().toString(), MARKER_RADIUS * resize, MARKER_RADIUS * resize, true, true);
                Circle circle = new Circle(MARKER_RADIUS * resize);
                circle.setFill(new ImagePattern(img));
                gridPane.add(circle, i, 1);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }


            //GridPane.setConstraints(bonusTileArray[i].getImage(), i, 0);
        }


        vbox.setId("endGamePopup");
        vbox.getChildren().addAll(introLabel, gridPane);
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
        popupStage.show();
    }

}
