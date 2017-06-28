package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by miotto on 27/06/17.
 */
public class CardWidget {

    public static final double CARD_MIN_HEIGHT = 100;
    public static final double CARD_MIN_WIDTH = 70;

    private int referenceID;
    private boolean empty;
    private ImageView cardImage;
    private String imagePath;

    public CardWidget() {

    }

    public CardWidget(String path) {
        Image i = new Image(path, CARD_MIN_WIDTH, CARD_MIN_HEIGHT, true, true);
        cardImage = new ImageView();
        cardImage.setImage(i);

        cardImage.setOnMouseClicked((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
        });
    }

    public boolean isEmpty() {
        return this.empty;
    }

}
