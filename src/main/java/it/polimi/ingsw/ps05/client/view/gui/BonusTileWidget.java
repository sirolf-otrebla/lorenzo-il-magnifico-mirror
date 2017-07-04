package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by miotto on 04/07/17.
 */
public class BonusTileWidget extends ImageView {

    private int referenceId;
    private boolean drafted;
    private boolean thisPlayerSelected;
    private String imagePath;

    public BonusTileWidget(int referenceId) {
        this.referenceId = referenceId;
        // obtain image path from referenceId
        this.imagePath = GraphicResources.getBonusTilePath(referenceId);

        addImage(this.imagePath);
    }

    public void addImage(String imagePath) {
        Image img = new Image(imagePath);
        this.setImage(img);
        this.setupClickGesture();
    }

    // click-to-take-bonusTile gesture
    private void setupClickGesture() {
        this.setOnMouseClicked((MouseEvent e) -> {
            this.setDrafted(true);
            this.setThisPlayerSelected(true);
            //TODO si potrebbe chiamare il metodo repaint() direttamente da qui, senza aspettare la chiamata del controller
        });
    }

    public void repaint() {
        if(isDrafted()) {
            this.setOpacity(0.5);
            this.setMouseTransparent(true);
        }
    }


    public boolean isThisPlayerSelected() {
        return thisPlayerSelected;
    }

    public void setThisPlayerSelected(boolean thisPlayerSelected) {
        this.thisPlayerSelected = thisPlayerSelected;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public boolean isDrafted() {
        return drafted;
    }

    public void setDrafted(boolean drafted) {
        this.drafted = drafted;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
