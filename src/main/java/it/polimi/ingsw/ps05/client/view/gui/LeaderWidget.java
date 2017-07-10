package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.stageHeight;

/**
 * Created by miotto on 28/06/17.
 */
public class LeaderWidget extends ImageView{

    public static final double LEADER_HEIGHT_PERC = 0.4;

    private static final String backPath = "./src/main/res/img/cards/leaders/leaders_BACK.jpg";

    private int referenceId;
    private String imagePath;
    private static boolean played;
    private boolean activable;
    private boolean active;
    private boolean drafted;
    private boolean discarded;
    private boolean draftFinished;
    private boolean ofPlayerProperty; //TODO si dovrebbe poter settare dal controller

    public LeaderWidget() {
    }

    public LeaderWidget(int referenceId) {
        this.referenceId = referenceId;
        System.out.println(referenceId);
        String imagePath = GraphicResources.getLeaderPath(referenceId);
        this.imagePath = imagePath;
        System.out.println(imagePath);
        addImage(imagePath);
    }

    public void addImage(String imagePath) {
        File crDir = new File(imagePath);
        try {
            Image img = new Image(crDir.toURI().toURL().toString());
            this.setImage(img);
            this.setFitHeight(LEADER_HEIGHT_PERC * stageHeight);
            this.setPreserveRatio(true);
            this.setSmooth(true);
            System.out.println("image added");
            //this.setupClickGesture();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    /*
    public void addBackImage() {
        File crDir = new File(backPath);
        try {
            Image i = new Image(crDir.toURI().toURL().toString());
            backSide = new ImageView(i);
            backSide.setPreserveRatio(true);
            backSide.setSmooth(true);
            backSide.setCache(true);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

    }
    */


    private void setupZoomGesture() {
        this.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            if(e.getClickCount() == 1) {
                //TODO: implementare lo zoom
            }
        });
    }

    private void setupClickGesture() {

        if(!isDraftFinished()) {
            this.setOnMouseEntered((MouseEvent e) -> {
                this.setCursor(Cursor.HAND);
            });

            this.setOnMouseClicked((MouseEvent e) -> {
                //TODO notificare il controller
                this.setOpacity(0.3);
                this.setMouseTransparent(true);
            });
        }


        if(isOfPlayerProperty() && !isActive())
            this.setOnMouseClicked((MouseEvent e) -> {
                played = LeaderActivationPopup.display(); //TODO evento da collegare col controller (il metodo ritorna la scelta del giocatore)
            });
    }

    public void repaint() {
        //TODO aggiungere caso carta pescata (se serve)
        if(isOfPlayerProperty()) {
            if (isDiscarded()) {
                addImage(null);
                this.setMouseTransparent(true);
            } else if (!isPlayed() || isActive() || (isPlayed() && isActive())) {
                addImage(this.imagePath);
                this.setMouseTransparent(false);
            } else if (isPlayed() && !isActive()) {
                //TODO non visible, si deve vedere il retro
                this.setMouseTransparent(false);
            }
        } else {
            if (isDiscarded()) {
                addImage(null);
                this.setMouseTransparent(true);
            } else if (isPlayed() && isActive()) {
                addImage(this.imagePath);
                this.setMouseTransparent(false);
            } else if (isPlayed() && !isActive()) {
                //TODO non visible, si deve vedere il retro
                this.setMouseTransparent(true);
            } else if (!isPlayed()) {
                //TODO non visibile, si deve vedere il retro
                this.setMouseTransparent(true);
            }
        }
    }

    public void repaintDraft() {

    }

    public int getReferenceID() {
        return referenceId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDrafted() {
        return drafted;
    }

    public void setDrafted(boolean drafted) {
        this.drafted = drafted;
    }

    public boolean isOfPlayerProperty() {
        return ofPlayerProperty;
    }

    public void setOfPlayerProperty(boolean ofPlayerProperty) {
        this.ofPlayerProperty = ofPlayerProperty;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public boolean isDraftFinished() {
        return draftFinished;
    }

    public void setDraftFinished(boolean draftFinished) {
        this.draftFinished = draftFinished;
    }

    public boolean isActivable() {
        return activable;
    }

    public void setActivable(boolean activable) {
        this.activable = activable;
    }
}
