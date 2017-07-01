package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;
import java.awt.*;

/**
 * Created by miotto on 28/06/17.
 */
public class FamiliarWidget {

    public static final double FAMILIAR_MIN_SIZE = 40;
    private int referenceID;
    private boolean isPlaced;
    private ImageView imageElement;
    private String imagePath;
    private ColorEnumeration FamiliarColor;
    private ColorEnumeration PlayerColor;

    public FamiliarWidget(ColorEnumeration player, ColorEnumeration Familiar) {


        //todo
    }

    public FamiliarWidget(String path) {
        Image i = new Image(path, FAMILIAR_MIN_SIZE * resize, FAMILIAR_MIN_SIZE * resize, true, true);
        imageElement = new ImageView();
        imageElement.setImage(i);

        setupGestureSource();
    }

    void setupGestureSource() {

        imageElement.setOnDragDetected((MouseEvent e) -> {

            imageElement.setCursor(Cursor.CLOSED_HAND);

            /* drag was detected, start a drag-and-drop gesture */
            /* allow MOVE transfer mode */
            Dragboard db = imageElement.startDragAndDrop(TransferMode.MOVE);

            /* Put the image on the dragboard */
            ClipboardContent content = new ClipboardContent();
            Image imageElementImage = imageElement.getImage();
            content.putImage(imageElementImage);
            db.setContent(content);

            /* Check which targets are allowed */
            //TODO: per controllare quali posti azione sono utilizzabili bisogna passare oltre all'immagine del familiare
            //TODO: anche il valore del dado (volendo anche le risorse così si controlla oltre al dado anche il costo delle carta
            showAllowedActionSpaces();

            //e.consume();

        });

        imageElement.setOnMouseEntered((MouseEvent e) -> {
            imageElement.setCursor(Cursor.HAND);
        });

        imageElement.setOnDragDone((DragEvent e) -> {
            /* Actions to be performed after the drag is completed */
            if (e.getTransferMode() == TransferMode.MOVE) {
                imageElement.setImage(null);
            }

            //e.consume();
        });

    }

    public ImageView getImageElement() {
        return this.imageElement;
    }

    private void showAllowedActionSpaces() {
        //TODO: capire come mostrare gli spazi azione abilitati in base al valore del familiare e ai servitori del giocatore
    }

}
