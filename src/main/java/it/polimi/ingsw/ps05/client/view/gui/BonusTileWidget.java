package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageHeight;

/**
 * Created by miotto on 04/07/17.
 */
public class BonusTileWidget {

    private static double BONUSTILE_HEIGHT_PERC = 0.6;

    private int referenceId;
    private boolean drafted;
    private boolean thisPlayerSelected;
    private ImageView image;
    private String imagePath;

    public BonusTileWidget() {

    }
    public BonusTileWidget(int referenceId) {
        this.referenceId = referenceId;
        // obtain image path from referenceId
        this.imagePath = GraphicResources.getBonusTilePath(referenceId);
        addImage(this.imagePath);
    }

    public void addImage(String imagePath) {
        File crDir = new File(imagePath);
        try {
            Image img = new Image(crDir.toURI().toURL().toString());
            this.image = new ImageView(img);
            this.image.setPreserveRatio(true);
            this.image.setFitHeight(BONUSTILE_HEIGHT_PERC * stageHeight);
            this.setupClickGesture();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    // click-to-take-bonusTile gesture
    private void setupClickGesture() {

        this.image.setOnMouseEntered((MouseEvent e) -> {
            this.image.setCursor(Cursor.HAND);
        });

        this.image.setOnMouseClicked((MouseEvent e) -> {
            this.setDrafted(true);
            this.setThisPlayerSelected(true);
            this.repaint();
        });
    }

    public void repaint() {
        if(isDrafted()) {
            this.image.setOpacity(0.3);
            this.image.setMouseTransparent(true);
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

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
