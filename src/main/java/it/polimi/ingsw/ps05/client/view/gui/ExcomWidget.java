package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageHeight;

/**
 * Created by miotto on 27/06/17.
 */
public class ExcomWidget extends ImageView {

    /* LARGHEZZA DA CONTROLLARE */
    public static final double EXCOM_MIN_WIDTH = 48.34;
    public static final double EXCOM_MIN_HEIGHT = 85.0;

    private int referenceId;
    private String imagePath;
    private String description; //TODO da aggiungere la descrizione
    private boolean isActive; // forse è un attributo inutile


    public ExcomWidget() {
        // create empty widget
    }

    public ExcomWidget(int referenceId) {
        try {
            addExcom(referenceId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.setOnMouseClicked((MouseEvent e) -> {
            /* Actions to be performed when the excommunication card is clicked once */
            //TODO: implementare lo zoom
        });
    }

    public void addExcom(int referenceId) throws MalformedURLException {
        this.imagePath = GraphicResources.getExcomPath(referenceId);
        System.out.println(this.imagePath);
        File file = new File(this.imagePath);
        this.imagePath = file.toURI().toURL().toString();
        Image img = new Image(this.imagePath, EXCOM_MIN_WIDTH * resize, EXCOM_MIN_HEIGHT * resize, true, true);
        this.setImage(img);
        this.setFitHeight((20 / 100) * stageHeight);
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

}
