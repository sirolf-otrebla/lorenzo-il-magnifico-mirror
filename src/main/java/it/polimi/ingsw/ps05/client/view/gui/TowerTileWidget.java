package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

/**
 * Created by miotto on 27/06/17.
 */
public class TowerTileWidget extends SingleOccupantActionSpaceWidget {

    private CardOnBoardWidget associatedCard;
    private boolean morePaymentOptions;
    private boolean isLegal = false;
    private ArrayList<ColorEnumeration> legalFamilyMemberList = new ArrayList<>();


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
                    /* Showing payment selection window if associated card has payment alternatives */
                    PaymentPopup paymentPopup = new PaymentPopup();
                    paymentPopup.setResArrayList(null); //TODO: come si passano le risorse tra cui scegliere al popup?
                    if (paymentPopup.display(this.getAssociatedCard().getCardName())) {
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

    public CardOnBoardWidget getAssociatedCard() {
        return associatedCard;
    }

    public void setAssociatedCard(CardOnBoardWidget associatedCard) {
        this.associatedCard = associatedCard;
    }


    public boolean hasMorePaymentOptions() {
        return morePaymentOptions;
    }


    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
    }

    public ArrayList<ColorEnumeration> getLegalFamilyMemberList() {
        return legalFamilyMemberList;
    }

    public void setLegalFamilyMemberList(ArrayList<ColorEnumeration> legalFamilyMemberList) {
        this.legalFamilyMemberList = legalFamilyMemberList;
    }
}
