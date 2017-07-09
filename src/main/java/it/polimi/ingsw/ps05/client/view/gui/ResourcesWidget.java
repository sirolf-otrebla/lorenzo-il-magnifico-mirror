package it.polimi.ingsw.ps05.client.view.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;


/**
 * Created by miotto on 03/07/17.
 */
public class ResourcesWidget {

    private int[] resources;
    private Label goldLabel, woodLabel, stoneLabel, servantLabel;
    private HBox hbox;

    public ResourcesWidget() {
        this.goldLabel = new Label();
        this.woodLabel = new Label();
        this.stoneLabel = new Label();
        this.servantLabel = new Label();
        this.hbox = new HBox();
    }

    public HBox setupThisPlayerResource() {
        this.goldLabel.setText(String.valueOf(resources[GraphicResources.getResource("Oro")]));
        this.woodLabel.setText(String.valueOf(resources[GraphicResources.getResource("Legno")]));
        this.stoneLabel.setText(String.valueOf(resources[GraphicResources.getResource("Pietra")]));
        this.servantLabel.setText(String.valueOf(resources[GraphicResources.getResource("Servitori")]));

        this.hbox.setSpacing(20 * resize);
        this.hbox.setLayoutX((68.0 / 100) * stageWidth);
        this.hbox.setLayoutY((32.08 / 100) * stageHeight);
        this.hbox.setFillHeight(true);
        this.hbox.setPrefHeight((4.8 / 100) * stageHeight);

        this.hbox.getChildren().addAll(goldLabel, woodLabel, stoneLabel, servantLabel);

        return hbox;
    }

    public void display() {

        Stage stage = new Stage();

        this.goldLabel.setText(String.valueOf(resources[GraphicResources.getResource("Oro")]));
        this.woodLabel.setText(String.valueOf(resources[GraphicResources.getResource("Legno")]));
        this.stoneLabel.setText(String.valueOf(resources[GraphicResources.getResource("Pietra")]));
        this.servantLabel.setText(String.valueOf(resources[GraphicResources.getResource("Servitori")]));

        this.goldLabel.setId("goldLabel");
        this.woodLabel.setId("woodLabel");
        this.stoneLabel.setId("stoneLabel");
        this.servantLabel.setId("servantLabel");

        hbox.setSpacing(60 * resize);
        hbox.setId("resourcePopup");
        hbox.setLayoutX((68.0 / 100) * stageWidth);
        hbox.setLayoutY((32.08 / 100) * stageHeight);
        hbox.setFillHeight(true);
        hbox.setPrefHeight((4.8 / 100) * stageHeight);
        hbox.getChildren().addAll(goldLabel, woodLabel, stoneLabel, servantLabel);

        ImageView resourcesBackground = new ImageView();
        File resFile = new File("./src/main/res/img/resources.png");
        try {
            Image img = new Image(resFile.toURI().toURL().toString());
            resourcesBackground.setImage(img);
            resourcesBackground.setPreserveRatio(true);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(resourcesBackground, hbox);

        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void repaint() {

        goldLabel.setText(String.valueOf(resources[GraphicResources.getResource("Oro")]));
        woodLabel.setText(String.valueOf(resources[GraphicResources.getResource("Legno")]));
        stoneLabel.setText(String.valueOf(resources[GraphicResources.getResource("Pietra")]));
        servantLabel.setText(String.valueOf(resources[GraphicResources.getResource("Servitori")]));

    }

    public void setResource(String stringId, int newValue){
        int i = resources[GraphicResources.getResource(stringId)];
        resources[i] = newValue;
    }

}
