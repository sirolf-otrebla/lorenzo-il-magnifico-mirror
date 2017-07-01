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
    private ImageView cardImage;
    private String imagePath;
    private String cardName;
    private boolean morePaymentOptions;
    private boolean taken;

    public CardWidget(Integer referenceID) {
        this.referenceID = referenceID;
        //TODO FINIRE COSTRUTTORE E SETTARE PATH

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

    public void repaint() {
        if(isTaken()) {
            this.cardImage.setImage(null);
        }
        else {
            Image i = new Image(imagePath, CARD_MIN_WIDTH, CARD_MIN_HEIGHT, true, true);
            this.cardImage.setImage(i);
        }
    }

    public String getCardName() {
        return cardName;
    }

    public void setReferenceID(int referenceID) {
        this.referenceID = referenceID;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean hasMorePaymentOptions() {
        return this.morePaymentOptions;
    }

    public void setMorePaymentOptions(boolean morePaymentOptions) {
        this.morePaymentOptions = morePaymentOptions;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setCardImage(ImageView cardImage) {
        this.cardImage = cardImage;
    }

    public int getReferenceID() {
        return referenceID;
    }
}
