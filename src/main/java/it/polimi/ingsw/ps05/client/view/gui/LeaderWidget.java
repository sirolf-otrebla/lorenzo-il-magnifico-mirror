package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by miotto on 28/06/17.
 */
public class LeaderWidget {

    private static final String backPath = "./src/main/res/img/cards/leaders/leaders_BACK.jpg";

    private int referenceId;
    private String imagePath;
    private ImageView leaderCard;
    private ImageView backSide;
    private static boolean played;
    private boolean active;
    private boolean drafted;
    private boolean discarded;
    private boolean draftFinished;
    private boolean ofPlayerProperty; //TODO si dovrebbe poter settare dal controller

    public LeaderWidget() {
        addBackImage();
    }

    public LeaderWidget(int referenceId) {
        this.referenceId = referenceId;
        //TODO
        addBackImage();
    }

    public LeaderWidget(int referenceId, String imagePath) {
        this.referenceId = referenceId;
        addImage(imagePath);
        addBackImage();
        setupZoomGesture();
    }

    public void addImage(String imagePath) {
        Image img = new Image(imagePath);
        this.leaderCard = new ImageView(img);
        this.leaderCard.setPreserveRatio(true);
        this.leaderCard.setSmooth(true);
        this.leaderCard.setCache(true); // performance boost
    }

    public void addBackImage() {
        Image i = new Image(backPath);
        this.backSide = new ImageView(i);
        this.backSide.setPreserveRatio(true);
        this.backSide.setSmooth(true);
        this.backSide.setCache(true);
    }


    private void setupZoomGesture() {
        this.leaderCard.setOnMouseEntered((MouseEvent e) -> {
            /* Actions to be performed when the card is clicked once */
            if(e.getClickCount() == 1) {
                //TODO: implementare lo zoom
            }
        });
    }

    private void setupClickGesture() {
        if(this.isOfPlayerProperty() && !this.isActive())
            this.leaderCard.setOnMouseClicked((MouseEvent e) -> {
                played = LeaderActivationPopup.display(); //TODO evento da collegare col controller (il metodo ritorna la scelta del giocatore)
            });
    }

    public void repaint() {
        //TODO aggiungere caso carta pescata (se serve)
        if(isOfPlayerProperty()) {
            if (isDiscarded()) {
                addImage(null);
                this.leaderCard.setMouseTransparent(true);
            } else if (!isPlayed() || isActive() || (isPlayed() && isActive())) {
                addImage(this.imagePath);
                this.leaderCard.setMouseTransparent(false);
            } else if (isPlayed() && !isActive()) {
                //TODO non visible, si deve vedere il retro
                this.leaderCard.setMouseTransparent(false);
            }
        } else {
            if (isDiscarded()) {
                addImage(null);
                this.leaderCard.setMouseTransparent(true);
            } else if (isPlayed() && isActive()) {
                addImage(this.imagePath);
                this.leaderCard.setMouseTransparent(false);
            } else if (isPlayed() && !isActive()) {
                //TODO non visible, si deve vedere il retro
                this.leaderCard.setMouseTransparent(true);
            } else if (!isPlayed()) {
                //TODO non visibile, si deve vedere il retro
                this.leaderCard.setMouseTransparent(true);
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

    public ImageView getLeaderCard() {
        return leaderCard;
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

    public ImageView getBackSide() {
        return backSide;
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
}
