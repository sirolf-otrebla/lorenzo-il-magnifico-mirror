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
public class BonusTileWidget extends ImageView {

    private static double BONUSTILE_HEIGHT_PERC = 0.6;

    private int referenceId;
    private boolean drafted;
    private boolean draftFinished;
    private boolean thisPlayerSelected;
    private String imagePath;

    public BonusTileWidget() {

    }
    public BonusTileWidget(int referenceId) {
        this.setDrafted(false);
        this.setDraftFinished(false);
        this.referenceId = referenceId;
        // obtain image path from referenceId
        this.imagePath = GraphicResources.getBonusTilePath(referenceId);
        addImage(this.imagePath);
    }

    public void addImage(String imagePath) {
        File crDir = new File(imagePath);
        try {
            Image img = new Image(crDir.toURI().toURL().toString());
            this.setImage(img);
            this.setPreserveRatio(true);
            this.setFitHeight(BONUSTILE_HEIGHT_PERC * stageHeight);
            this.setupClickGesture();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    // click-to-take-bonusTile gesture
    private void setupClickGesture() {

        if(!isDraftFinished()) {
            this.setOnMouseEntered((MouseEvent e) -> {
                this.setCursor(Cursor.HAND);
            });

            this.setOnMouseClicked((MouseEvent e) -> {
                this.setDrafted(true);
                this.setThisPlayerSelected(true);
                this.repaint();
            });
        }
    }

    public void repaint() {
        if(!isDraftFinished()) {
            if (isDrafted()) {
                this.setOpacity(0.3);
                this.setMouseTransparent(true);
            }
        } else {
            this.setOpacity(1);
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

    public boolean isDraftFinished() {
        return draftFinished;
    }

    public void setDraftFinished(boolean draftFinished) {
        this.draftFinished = draftFinished;
    }
}
