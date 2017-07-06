package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.resourcesandbonuses.FaithResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.MilitaryResource;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.VictoryResource;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 28/06/17.
 */
public class MarkerWidget {

    public static final double MARKER_RADIUS = 14;
    public static final int INITIAL_POINTS = 0;

    public static final String VICTORY = VictoryResource.ID;
    public static final String MILITARY = MilitaryResource.id;
    public static final String FAITH = FaithResource.ID;
    public static final String PLAYER_ORDER = "playerOrder";



    private String referenceID;
    private StackPane stack;
    private Circle markerCircle;
    private Integer intPoints;
    private Text points;
    private String imagePath;

    public MarkerWidget() {

        markerCircle = new Circle(MARKER_RADIUS * resize);

        points = new Text(String.valueOf(INITIAL_POINTS));
        points.setFont(Font.loadFont("file:source/resources/fonts/im-fell-french-canon.ttf", 10));
        points.setBoundsType(TextBoundsType.VISUAL); // Calculate text bounds basing on visuals


        stack = new StackPane();
        stack.setMinSize(2 * MARKER_RADIUS * resize, 2 * MARKER_RADIUS * resize);
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(markerCircle, points);

    }

    public MarkerWidget(String backgroundPath) throws MalformedURLException {

        File file = new File(backgroundPath);

        imagePath = file.toURI().toURL().toString();
        Image markerImage = new Image(imagePath, 2 * MARKER_RADIUS * resize, 2 * MARKER_RADIUS * resize, true, true);
        markerCircle = new Circle(MARKER_RADIUS * resize);
        markerCircle.setFill(new ImagePattern(markerImage));

        points = new Text(String.valueOf(INITIAL_POINTS)); //TODO: controllare come si può fare meglio la trasformazione
        points.setFont(Font.loadFont("file:source/resources/fonts/im-fell-french-canon.ttf", 10));
        points.setBoundsType(TextBoundsType.VISUAL); // Calculate text bounds basing on visuals

        stack = new StackPane();
        stack.setMinSize(2 * MARKER_RADIUS * resize, 2 * MARKER_RADIUS * resize);
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(markerCircle, points);

    }

    public void addImage(String imagePath) {
        this.imagePath = imagePath;
        Image markerImage = new Image(imagePath, 2 * MARKER_RADIUS * resize, 2 * MARKER_RADIUS * resize, true, true);
        markerCircle.setFill(new ImagePattern(markerImage));
    }

    public void setID(String ID) {
        referenceID = ID;
    }

    public String getID() {
        return referenceID;
    }

    public StackPane getMarker() {
        return stack;
    }

    public Integer getIntPoints() {
        return intPoints;
    }

    public void setIntPoints(Integer intPoints) {
        this.intPoints = intPoints;
    }
    public Text getPoints() {
        return points;
    }
}

