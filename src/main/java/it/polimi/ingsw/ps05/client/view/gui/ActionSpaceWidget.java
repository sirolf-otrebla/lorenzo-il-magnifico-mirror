package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;


public class ActionSpaceWidget {

    private int referenceID;
    private boolean isOccupied;
    private Circle occupationCircle;
    private ColorEnumeration familyMemberID;
    private Integer occupantPlayerID;
    private Integer id;

    public ActionSpaceWidget() {
        occupationCircle = new Circle(FAMILIAR_MIN_SIZE / 2);
        isOccupied = false;
    }

    public Circle getOccupationCircle() {
        return this.occupationCircle;
    }

    public void setupGestureTarget() {

        occupationCircle.setOnDragOver((DragEvent e) -> {

            /* source is dragged over the occupationCircle */

            if (e.getGestureSource() != occupationCircle && e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });

        occupationCircle.setOnDragEntered((DragEvent e) -> {
            if (e.getGestureSource() != occupationCircle && e.getDragboard().hasImage()) {
                occupationCircle.setOpacity(0.4);
                occupationCircle.setFill(Color.FORESTGREEN);
            }

            //e.consume();
        });

        occupationCircle.setOnDragExited((DragEvent e) -> {

            occupationCircle.setFill(Color.TRANSPARENT);
            //e.consume();

        });

        occupationCircle.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(e.getDragboard().hasImage()) {
                System.out.println("inside if");
                Image source = e.getDragboard().getImage();
                occupationCircle.setOpacity(1);
                occupationCircle.setFill(Color.ALICEBLUE/*new ImagePattern(source)*/);
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public ColorEnumeration getFamilyMemberID() {
        return familyMemberID;
    }

    public int getOccupantID() {
        return occupantPlayerID;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
