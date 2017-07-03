package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;


/**
 * Created by miotto on 03/07/17.
 */
public class ResourcesWidget {

    private int goldValue, woodValue, stoneValue, servantValue;
    private Label goldLabel, woodLabel, stoneLabel, servantLabel;
    private HBox hbox;

    public void display() {

        Stage stage = new Stage();

        this.goldLabel = new Label(String.valueOf(goldValue));
        this.woodLabel = new Label(String.valueOf(woodValue));
        this.stoneLabel = new Label(String.valueOf(stoneValue));
        this.servantLabel = new Label(String.valueOf(servantValue));

        this.hbox = new HBox(20 * resize);
        this.hbox.setId("resourceLabel");
        this.hbox.setLayoutX((68.0 / 100) * stageWidth);
        hbox.setLayoutY((32.08 / 100) * stageHeight);
        hbox.setFillHeight(true);
        hbox.setPrefHeight((4.8 / 100) * stageHeight);

        hbox.getChildren().addAll(goldLabel, woodLabel, stoneLabel, servantLabel);

        //TODO hbox da inserire nella personalBoard degli avversari tramite PopOver

    }

    public HBox setupPersonalResource() {
        this.goldLabel = new Label(String.valueOf(goldValue));
        this.woodLabel = new Label(String.valueOf(woodValue));
        this.stoneLabel = new Label(String.valueOf(stoneValue));
        this.servantLabel = new Label(String.valueOf(servantValue));

        this.hbox = new HBox(20 * resize);
        this.hbox.setLayoutX((68.0 / 100) * stageWidth);
        this.hbox.setLayoutY((32.08 / 100) * stageHeight);
        this.hbox.setFillHeight(true);
        this.hbox.setPrefHeight((4.8 / 100) * stageHeight);

        this.hbox.getChildren().addAll(goldLabel, woodLabel, stoneLabel, servantLabel);

        return hbox;
    }

    public void repaint() {

        goldLabel.setText(String.valueOf(goldValue));
        woodLabel.setText(String.valueOf(woodValue));
        stoneLabel.setText(String.valueOf(stoneValue));
        servantLabel.setText(String.valueOf(servantValue));

    }

    public int getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(int goldValue) {
        this.goldValue = goldValue;
    }

    public int getWoodValue() {
        return woodValue;
    }

    public void setWoodValue(int woodValue) {
        this.woodValue = woodValue;
    }

    public int getStoneValue() {
        return stoneValue;
    }

    public void setStoneValue(int stoneValue) {
        this.stoneValue = stoneValue;
    }

    public int getServantValue() {
        return servantValue;
    }

    public void setServantValue(int servantValue) {
        this.servantValue = servantValue;
    }
}
