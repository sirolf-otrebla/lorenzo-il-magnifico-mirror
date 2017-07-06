package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class TowerCardWidget {

    public static final double CARD_MIN_HEIGHT_BOARD = 100;
    public static final double CARD_MIN_WIDTH_BOARD = 70;
    public static final int CARD_COLOR_DECK = 24;


    private int referenceId;
    private ImageView cardImage;
    private String imagePath;
    private String cardName;
    private boolean morePaymentOptions;
    private boolean taken;
    private ColorEnumeration color;

    public TowerCardWidget() {
        this.cardImage = new ImageView();
        setZoomGesture();
    }

    public TowerCardWidget(Integer referenceId) {

        this.color = GraphicResources.getCardColor(referenceId % CARD_COLOR_DECK);
        this.cardImage = new ImageView(); // create empty ImageView
        addCardImage(referenceId); // add image file to ImageView

        cardImage.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom

        });
    }

    public void addCardImage(Integer referenceId) {
        this.referenceId = referenceId;
        String path = GraphicResources.getCardPath(referenceId);
        this.imagePath = path;
        File crDir = new File(path);
        try {
            Image img = new Image(crDir.toURI().toURL().toString(), CARD_MIN_WIDTH_BOARD * resize, CARD_MIN_HEIGHT_BOARD * resize, true, true);
            cardImage.setImage(img);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void repaint() {
        if(isTaken()) {
            this.cardImage.setImage(null);
        }
        else { //TODO: forse questo else è inutile, da verificare
            Image i = new Image(imagePath, CARD_MIN_WIDTH_BOARD * resize, CARD_MIN_HEIGHT_BOARD * resize, true, true);
            this.cardImage.setImage(i);
        }
    }

    private void setZoomGesture() {
        cardImage.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
            File crDir = new File(imagePath);
            try {
                Image img = new Image(crDir.toURI().toURL().toString());
                zoomReference.setImage(img);
            } catch (MalformedURLException exc){
                exc.printStackTrace();
            }
        });

        //TODO volendo si può togliere la carta zoomata quando il mouse esce dalla carta
    }

    // riceve la plancia del giocatore e inserisce la carta acquistata nell'apposito spazio
    public void addToPersonalBoard(PersonalBoardWindow personalBoard) {

        HBox destinationBox = getCorrespondingBox(personalBoard);
        AcquiredCardWidget acquiredCard = new AcquiredCardWidget(this.referenceId, this.getImagePath());
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
