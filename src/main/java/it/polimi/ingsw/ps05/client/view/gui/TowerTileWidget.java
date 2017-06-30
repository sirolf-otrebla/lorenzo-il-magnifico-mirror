package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.spaces.Tower;
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

    public TowerTileWidget(int minDie) {
        super(minDie);
    }

    @Override
    public void setupDragDropped() {
        this.getOccupationCircle().setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(!isOccupied() && e.getDragboard().hasImage()) {
                if(this.associatedCard.hasMorePaymentOptions()) {
                    /* Showing selection window if card has payment alternatives */
                    if (PaymentPopup.display(this.getAssociatedCard().getCardName())) {
                        /* Enter 'if' when the player succesfully select the payment, without canceling action */
                        this.setOccupied(true);
                        System.out.println("inside if");
                        Image source = e.getDragboard().getImage();
                        this.getOccupationCircle().setOpacity(1);
                        this.getOccupationCircle().setFill(new ImagePattern(source));
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
