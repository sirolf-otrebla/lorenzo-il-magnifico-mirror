package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static it.polimi.ingsw.ps05.client.view.gui.PersonalBoardWindow.personalBoardResize;


/**
 * Created by miotto on 02/07/17.
 */
public class CardOnPersonalWidget {

    public static final double CARD_ORIGINAL_PERC_HEIGHT_PERSONAL = 20.1052, CARD_RATIO = 0.6743;

    private int referenceId;
    private ImageView cardImage;
    private String imagePath;
    private String cardName;
    private ColorEnumeration color;

    public CardOnPersonalWidget(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public CardOnPersonalWidget(Integer referenceId, String path) {
        this.referenceId = referenceId;
        Image i = new Image(path, CARD_ORIGINAL_PERC_HEIGHT_PERSONAL * personalBoardResize, CARD_ORIGINAL_PERC_HEIGHT_PERSONAL * CARD_RATIO * personalBoardResize, true, true);
        cardImage = new ImageView();
        cardImage.setImage(i);

        cardImage.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
        });
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public ColorEnumeration getColor() {
        return color;
    }
}
