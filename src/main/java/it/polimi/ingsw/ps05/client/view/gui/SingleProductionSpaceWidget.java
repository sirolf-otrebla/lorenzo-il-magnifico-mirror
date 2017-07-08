package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarData.FAMILIAR_DATA;

/**
 * Created by miotto on 08/07/17.
 */
public class SingleProductionSpaceWidget extends SingleOccupantActionSpaceWidget {

    ArrayList<PairObject> selectedActivations;
    private PlayerWidget player;

    public SingleProductionSpaceWidget(int minDie, PlayerWidget player) {
        super(minDie);
        this.player = player;
    }

    @Override
    public void setupDragDropped() {
        this.getOccupationCircle().setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if (e.getDragboard().hasContent(FAMILIAR_DATA)) {
                FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
                Image img = new Image(sourceData.getFamiliarImagePath());
                System.out.println("inside production space drag dropped");
                selectedActivations = getPlayer().displayProductionPopup(getPlayer().getPersonalBoard().getProductionCards());
                //TODO comunicare al controller le scelte dell'utente
                this.getOccupationCircle().setOpacity(1);
                this.getOccupationCircle().setFill(new ImagePattern(img));
                this.setOccupied(true);
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public PlayerWidget getPlayer() {
        return player;
    }
}
