package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarData.FAMILIAR_DATA;

/**
 * Created by miotto on 27/06/17.
 */
public class TowerTileWidget extends SingleOccupantActionSpaceWidget {

    private TowerCardWidget associatedCard;
    private boolean morePaymentOptions;
    private ArrayList<ColorEnumeration> legalFamilyMemberList = new ArrayList<>();


    public TowerTileWidget(int minDie) {
        super(minDie);
    }

    @Override
    public void setupDragDropped() {
        this.getOccupationCircle().setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;
            FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
            boolean isLegal = legalActionMap.get(sourceData.getFamiliarColor());

            System.out.println("starting if");
            if(!isOccupied() && isLegal && e.getDragboard().hasContent(FAMILIAR_DATA)) {
                // AZIONE LEGALE
                if(this.associatedCard.hasMorePaymentOptions()) {
                    /* Showing payment selection window if associated card has payment alternatives */
                    PaymentPopup paymentPopup = new PaymentPopup();
                    paymentPopup.setResArrayList(null); //TODO: come si passano le risorse tra cui scegliere al popup?
                    success = false;
                    // AZIONE ANNULLATA
                    if (paymentPopup.display(this.getAssociatedCard().getCardName()) != -1) {
                        /* Enter 'if' when the player succesfully select the payment, without canceling action */
                        // set space occupied
                        this.setOccupied(true);
                        // insert familiar image
                        Image img = new Image(sourceData.getFamiliarImagePath());
                        this.getOccupationCircle().setOpacity(1);
                        this.getOccupationCircle().setFill(new ImagePattern(img));
                        // add card to the player collection
                        //TODO da comunicare al controller
                        // AZIONE COMPLETATA
                        success = true;
                    }
                } else {
                    /* Adding card to personal collection */
                    //TODO da comunicare al controller
                }
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public TowerCardWidget getAssociatedCard() {
        return associatedCard;
    }

    public void setAssociatedCard(TowerCardWidget associatedCard) {
        this.associatedCard = associatedCard;
    }

    public boolean hasMorePaymentOptions() {
        return morePaymentOptions;
    }

    public ArrayList<ColorEnumeration> getLegalFamilyMemberList() {
        return legalFamilyMemberList;
    }

    public void setLegalFamilyMemberList(ArrayList<ColorEnumeration> legalFamilyMemberList) {
        this.legalFamilyMemberList = legalFamilyMemberList;
    }
}
