package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;

import java.io.Serializable;

/**
 * Created by miotto on 02/07/17.
 */
public class FamiliarData implements Serializable {

    static final DataFormat FAMILIAR_DATA = new DataFormat("FamiliarData");


    // TODO dati che il familiare deve passare tramite drag & drop

    private ColorEnumeration playerColor;
    private ColorEnumeration familiarColor;
    String familiarImagePath;

    public FamiliarData(ColorEnumeration playerColor, ColorEnumeration familiarColor, String familiarImagePath) {
        this.playerColor = playerColor;
        this.familiarColor = familiarColor;
        this.familiarImagePath = familiarImagePath;
    }

    public FamiliarData(ColorEnumeration playerColor, ColorEnumeration familiarColor) {
        this.playerColor = playerColor;
        this.familiarColor = familiarColor;
    }

    public ColorEnumeration getPlayerColor() {
        return playerColor;
    }

    public ColorEnumeration getFamiliarColor() {
        return familiarColor;
    }

    public String getFamiliarImagePath() {
        return familiarImagePath;
    }

    public void setPlayerColor(ColorEnumeration playerColor) {
        this.playerColor = playerColor;
    }

    public void setFamiliarColor(ColorEnumeration familiarColor) {
        this.familiarColor = familiarColor;
    }

    public void setFamiliarImagePath(String familiarImagePath) {
        this.familiarImagePath = familiarImagePath;
    }
}
