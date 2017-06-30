package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Created by miotto on 28/06/17.
 */
public class MarkerWidget {

    public static final double MARKER_RADIUS = 14;
    public static final int INITIAL_POINTS = 0;

    private int referenceID;
    private StackPane stack;
    private Circle markerCircle;
    private Text points;
    private String imagePath;

    public MarkerWidget() {

        markerCircle = new Circle(MARKER_RADIUS);

        points = new Text(new String(String.valueOf(INITIAL_POINTS)));
        points.setFont(Font.loadFont("file:source/resources/fonts/im-fell-french-canon.ttf", 10));
        points.setBoundsType(TextBoundsType.VISUAL); // Calculate text bounds basing on visuals

        stack = new StackPane();
        stack.setMinSize(2 * MARKER_RADIUS, 2 * MARKER_RADIUS);
        stack.getChildren().addAll(markerCircle, points);

    }

    public MarkerWidget(String backgroundPath) {

        imagePath = backgroundPath;
        Image markerImage = new Image(imagePath, 2 * MARKER_RADIUS, 2 * MARKER_RADIUS, true, true);
        markerCircle = new Circle(MARKER_RADIUS);
        markerCircle.setFill(new ImagePattern(markerImage));

        points = new Text(new String(String.valueOf(INITIAL_POINTS))); //TODO: controllare come si può fare meglio la trasformazione
        points.setFont(Font.loadFont("file:source/resources/fonts/im-fell-french-canon.ttf", 10));
        points.setBoundsType(TextBoundsType.VISUAL); // Calculate text bounds basing on visuals

        stack = new StackPane();
        stack.setMinSize(2 * MARKER_RADIUS, 2 * MARKER_RADIUS);
        stack.getChildren().addAll(markerCircle, points);

    }

    public void addImage(String imagePath) {
        this.imagePath = imagePath;
        Image markerImage = new Image(imagePath, 2 * MARKER_RADIUS, 2 * MARKER_RADIUS, true, true);
        markerCircle.setFill(new ImagePattern(markerImage));
    }

    public void setLayout(double x, double y) {
        stack.setLayoutX(x);
        stack.setLayoutY(y);
    }

    public void setID(int ID) {
        referenceID = ID;
    }

    public int getID() {
        return referenceID;
    }

    public Circle getMarkerCircle() {
        return markerCircle;
    }
}

