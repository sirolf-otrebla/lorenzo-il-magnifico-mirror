package it.polimi.ingsw.ps05.client.view.gui;


import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Created by miotto on 03/07/17.
 */
public class DieWidget extends Label{

    private ColorEnumeration dieColor;
    private Integer value;


    public DieWidget(ColorEnumeration color) {
        this.dieColor = color;
        this.setText(""); // empty label
        //this.label.setFont(Font.loadFont("file:./src/main/res/fonts/JimNightshade-Regular.ttf", 38));
        this.getStyleClass().add("diewidget");
    }

    /*
    public DieWidget(ColorEnumeration color, int value) {
        this.dieColor = color;
        this.value = value;
        this.label = new Label(String.valueOf(value));
    }
    */

    /*
    public DieWidget(int referenceId, ColorEnumeration color, int value) {
        this(color, value);
        this.referenceId = referenceId;
    }
    */

    public void repaint() {
        this.setText(String.valueOf(value));
    }




    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ColorEnumeration getDieColor() {
        return dieColor;
    }

    public void setDieColor(ColorEnumeration dieColor) {
        this.dieColor = dieColor;
    }
}
