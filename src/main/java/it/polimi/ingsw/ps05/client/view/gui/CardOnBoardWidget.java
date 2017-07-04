package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;

/**
 * Created by miotto on 02/07/17.
 */
public class CardOnBoardWidget {

    public static final double CARD_MIN_HEIGHT_BOARD = 100;
    public static final double CARD_MIN_WIDTH_BOARD = 70;


    private int referenceId;
    private ImageView cardImage;
    private String imagePath;
    private String cardName;
    private boolean morePaymentOptions;
    private boolean taken;
    private ColorEnumeration color;

    public CardOnBoardWidget() {

    }

    public CardOnBoardWidget(Integer referenceId) {
        addCardImage(referenceId);

        cardImage.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
        });
    }

    public void addCardImage(Integer referenceId) {
        this.referenceId = referenceId;
        String path = GraphicResources.getCardPath(referenceId);
        File crDir = new File(path);
        try {
            Image i = new Image(crDir.toURI().toURL().toString(), CARD_MIN_WIDTH_BOARD * resize, CARD_MIN_HEIGHT_BOARD * resize, true, true);
            cardImage = new ImageView();
            cardImage.setImage(i);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void repaint() {
        if(isTaken()) {
            this.cardImage.setImage(null);
        }
        else {
            Image i = new Image(imagePath, CARD_MIN_WIDTH_BOARD * resize, CARD_MIN_HEIGHT_BOARD * resize, true, true);
            this.cardImage.setImage(i);
        }
    }

    // riceve la plancia del giocatore e inserisce la carta acquistata nell'apposito spazio
    public void addToPersonalBoard(PersonalBoardWindow personalBoard) {

        HBox destinationBox = getCorrespondingBox(personalBoard);
        CardOnPersonalWidget acquiredCard = new CardOnPersonalWidget(this.referenceId, this.getImagePath());
        destinationBox.getChildren().add(this.cardImage);

    }


    private HBox getCorrespondingBox(PersonalBoardWindow personalBoard) {

        for(int i = 0; i < 4; i++) {
            // find the right card box color and return
            if(this.color == GraphicResources.getCardColor(i))
                return personalBoard.getCardHboxes()[i];
        }
        return null; //TODO se non viene trovato il colore corrispondente si potrebbe lanciare un'eccezione o chiudere il programma
    }






    public String getCardName() {
        return cardName;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
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

    public int getReferenceId() {
        return referenceId;
    }

    public ColorEnumeration getColor() {
        return color;
    }

    public String getImagePath() {
        return imagePath;
    }
}
