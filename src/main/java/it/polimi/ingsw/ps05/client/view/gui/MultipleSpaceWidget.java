package it.polimi.ingsw.ps05.client.view.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;


/**
 * Created by miotto on 27/06/17.
 */
public class MultipleSpaceWidget {

    private int referenceID;
    private final ScrollPane scrollPane = new ScrollPane();
    private final HBox hbox = new HBox();

    public MultipleSpaceWidget() {
        hbox.setMinHeight(FAMILIAR_MIN_SIZE);
        hbox.setFillHeight(true);
        scrollPane.setMinSize(2 * FAMILIAR_MIN_SIZE, FAMILIAR_MIN_SIZE);
        scrollPane.setContent(hbox);

        /* disabling scrollbar */
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);

        setupGestureTarget();
    }

    public void setupGestureTarget() {
        /* Setting up drag event handlers */
        setupDragOver();
        setupDragEntered();
        setupDragExited();
        setupDragDropped();
    }

    public void setupDragOver() {
        scrollPane.setOnDragOver((DragEvent e) -> {

            /* source is dragged over the scrollbar */

            if (e.getGestureSource() != scrollPane && e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });
    }

    public void setupDragEntered() {
        scrollPane.setOnDragEntered((DragEvent e) -> {
            if (e.getGestureSource() != scrollPane && e.getDragboard().hasImage()) {
                scrollPane.setStyle("-fx-border-style: outset");
                scrollPane.setStyle("-fx-border-width: 3px");
                scrollPane.setStyle("-fx-border-color: palegreen");
            }

            //e.consume();
        });
    }

    public void setupDragExited() {
        scrollPane.setOnDragExited((DragEvent e) -> {

            scrollPane.setStyle("-fx-border-style: none");
            //e.consume();

        });
    }

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
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

}
