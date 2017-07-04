package it.polimi.ingsw.ps05.client.view.gui;


import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by miotto on 03/07/17.
 */
public class DieWidget {

    private int referenceId; //TODO controllare se i dadi necessitano di ID
    private Integer value;
    private Label valueLabel;
    private ColorEnumeration dieColor;

    public DieWidget(int referenceId) {
        this.dieColor = GraphicResources.getDiceColor(referenceId); // va da 0 a 2
        this.valueLabel = new Label("0"); // test
    }

    public DieWidget(ColorEnumeration color) {
        this.dieColor = color;
    }

    public DieWidget(ColorEnumeration color, int value) {
        this.dieColor = color;
        this.value = value;
        this.valueLabel = new Label(String.valueOf(value));
    }

    public DieWidget(int referenceId, ColorEnumeration color, int value) {
        this(color, value);
        this.referenceId = referenceId;
    }

    public void repaint() {
        this.valueLabel.setText(String.valueOf(value));
    }





    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Label getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(Label valueLabel) {
        this.valueLabel = valueLabel;
    }

    public ColorEnumeration getDieColor() {
        return dieColor;
    }

    public void setDieColor(ColorEnumeration dieColor) {
        this.dieColor = dieColor;
    }
}
