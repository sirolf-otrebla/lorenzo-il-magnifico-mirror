package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static it.polimi.ingsw.ps05.client.view.gui.PersonalBoardWindow.personalBoardResize;


/**
 * Created by miotto on 02/07/17.
 */
public class AcquiredCardWidget extends ImageView{

    public static final double CARD_ORIGINAL_PERC_HEIGHT_PERSONAL = 20.1052, CARD_RATIO = 0.6743;

    private int referenceId;
    private String imagePath;
    private String cardName;
    private ColorEnumeration color;
    private boolean activationMode; // forse inutile
    private boolean activationSelected;
    private boolean moreActivationAlternatives;


    public AcquiredCardWidget() {

    }

    public AcquiredCardWidget(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public AcquiredCardWidget(Integer referenceId, String path) {
        this.referenceId = referenceId;
        Image i = new Image(path, CARD_ORIGINAL_PERC_HEIGHT_PERSONAL * personalBoardResize, CARD_ORIGINAL_PERC_HEIGHT_PERSONAL * CARD_RATIO * personalBoardResize, true, true);
        this.setImage(i);

        this.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            //TODO: implementare lo zoom
        });
    }


    public String getCardName() {
        return cardName;
    }

    public ColorEnumeration getColor() {
        return color;
    }

    public boolean isActivationMode() {
        return activationMode;
    }

    public void setActivationMode(boolean activationMode) {
        this.activationMode = activationMode;
    }

    public boolean isActivationSelected() {
        return activationSelected;
    }

    public void setActivationSelected(boolean activationSelected) {
        this.activationSelected = activationSelected;
    }

    public boolean isMoreActivationAlternatives() {
        return moreActivationAlternatives;
    }

    public void setMoreActivationAlternatives(boolean moreActivationAlternatives) {
        this.moreActivationAlternatives = moreActivationAlternatives;
    }

    public int getReferenceId() {
        return referenceId;
    }
}
