package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by miotto on 27/06/17.
 */
public class HarvestingSpaceWidget extends MultipleSpaceWidget {

    private boolean morethanZeroOccupants = false;
    private ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList; // 1 player, 2 familiare
    private boolean isLegal;
    ArrayList<Integer> selectedActivations;

    public HarvestingSpaceWidget(int minimumDie, PlayerWidget player) {
        super(minimumDie, player);
    }

    @Override
    public void setupDragDropped() {
        scrollPane.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(e.getDragboard().hasImage()) {
                System.out.println("inside if");
                Image source = e.getDragboard().getImage();
                ImageView imageElement = new ImageView(source);
                hbox.getChildren().add(imageElement);
                // display popup
                selectedActivations = getPlayer().displayHarvestingPopup(getPlayer().getPersonalBoard().getProductionCards());
                //TODO comunicare al controller le scelte dell'utente
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }


    public boolean isMorethanZeroOccupants() {
        return morethanZeroOccupants;
    }

    public void setMorethanZeroOccupants(boolean morethanZeroOccupants) {
        this.morethanZeroOccupants = morethanZeroOccupants;
    }

    public ArrayList<Pair<ColorEnumeration, ColorEnumeration>> getOccupingFamiliarList() {
        return occupingFamiliarList;
    }

    public void setOccupingFamiliarList(ArrayList<Pair<ColorEnumeration, ColorEnumeration>> occupingFamiliarList) {
        this.occupingFamiliarList = occupingFamiliarList;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
    }
}
