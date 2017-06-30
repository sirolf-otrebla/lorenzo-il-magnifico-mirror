package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Created by miotto on 27/06/17.
 */
public class TowerTileWidget extends ActionSpaceWidget {

    private CardWidget associatedCard;
    private boolean morePaymentOptions;

    public TowerTileWidget() {
        super();
    }

    @Override
    public void setupDragDropped() {
         super.getOccupationCircle().setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(e.getDragboard().hasImage()) {
                if(this.associatedCard.hasPayAlternatives()) {
                    /* Showing selection window if card has payment alternatives */
                    if (PaymentPopup.display(this.getAssociatedCard().getCardName())) {
                        /* Enter 'if' when the player succesfully select the payment, without canceling action */
                        super.isOccupied = true;
                        System.out.println("inside if");
                        Image source = e.getDragboard().getImage();
                        occupationCircle.setOpacity(1);
                        occupationCircle.setFill(new ImagePattern(source));
                        success = true;
                    }
                }
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public CardWidget getAssociatedCard() {
        return associatedCard;
    }

    public void setAssociatedCard(CardWidget associatedCard) {
        this.associatedCard = associatedCard;
    }


    public boolean hasMorePaymentOptions() {
        return morePaymentOptions;
    }
}
