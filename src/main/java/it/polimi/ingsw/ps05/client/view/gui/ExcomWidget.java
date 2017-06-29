package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by miotto on 27/06/17.
 */
public class ExcomWidget {

    /* LARGHEZZA DA CONTROLLARE */
    public static final int EXCOM_MIN_WIDTH = 45;
    public static final int EXCOM_MIN_HEIGHT = 80;

    private int referenceID;
    private ImageView excomImage;
    private String imagePath;
    private boolean isActive; // forse è un attributo inutile

    public ExcomWidget() {

    }

    public ExcomWidget(String path) {
        Image i = new Image(path, EXCOM_MIN_WIDTH, EXCOM_MIN_HEIGHT, true, true);
        excomImage = new ImageView();
        excomImage.setImage(i);

        excomImage.setOnMouseClicked((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
        });
    }

}
