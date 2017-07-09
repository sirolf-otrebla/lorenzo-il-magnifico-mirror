package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;

/**
 * Created by miotto on 02/07/17.
 */
public class TowerCardWidget extends ImageView{

    public static final double CARD_MIN_HEIGHT_BOARD = 100;
    public static final double CARD_MIN_WIDTH_BOARD = 70;
    public static final int CARD_COLOR_DECK = 24;


    private int referenceId;
    private String imagePath;
    private String cardName;
    private boolean morePaymentOptions;
    private boolean taken;
    private ColorEnumeration color;

    public TowerCardWidget() {
        setZoomGesture();
    }

    public TowerCardWidget(Integer referenceId) {

        this.color = GraphicResources.getCardColor(referenceId % CARD_COLOR_DECK);
        addCardImage(referenceId); // add image file to ImageView

        this.setOnMouseEntered((MouseEvent e) -> {
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
            this.setImage(img);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void repaint() {
        if(isTaken()) {
            this.setImage(null);
        }
        else { //TODO: forse questo else è inutile, da verificare
            Image i = new Image(imagePath, CARD_MIN_WIDTH_BOARD * resize, CARD_MIN_HEIGHT_BOARD * resize, true, true);
            this.setImage(i);
        }
    }

    private void setZoomGesture() {
        this.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is pointed */
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
        /*
        cardImage.setOnMouseExited((MouseEvent e) -> {
            /* Actions to be performed when the card is left */
        /*
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                zoomReference.setImage(null);
            });
            pause.play();
        });
        */
    }

    // riceve la plancia del giocatore e inserisce la carta acquistata nell'apposito spazio
    public void addToPersonalBoard(PersonalBoardWindow personalBoard) {

        AcquiredCardWidget acquiredCard = new AcquiredCardWidget(this.referenceId, this.getImagePath(), this.color);

        personalBoard.getCardHboxesMap().get(color).getChildren().add(acquiredCard);


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
