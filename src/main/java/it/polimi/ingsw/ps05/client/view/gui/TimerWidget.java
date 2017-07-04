package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.Action;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by miotto on 03/07/17.
 */
public class TimerWidget extends Label{

    // private class constant and some variables
    private static final Integer STARTTIME = 120;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);;


    public void setupTimer() {

        // Configure the Label
        timerLabel.setId("timerLabel");
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setStyle("-fx-font-size: 4em;");

    }

    public void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME+1),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }
}

