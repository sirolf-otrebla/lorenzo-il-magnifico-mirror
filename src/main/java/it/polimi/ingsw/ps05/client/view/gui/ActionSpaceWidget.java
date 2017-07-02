package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarWidget.FAMILIAR_MIN_SIZE;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.resize;


public class ActionSpaceWidget {

    private int referenceId;
    private boolean occupied;
    private Circle occupationCircle;
    private ColorEnumeration familyMemberId;
    private ColorEnumeration occupantPlayerId;
    private Integer id;
    private boolean isOccupied;
    private GraphicResources graphicMap = new GraphicResources();
    private int minDie;

    public ActionSpaceWidget(int minimumDie) {
        occupationCircle = new Circle(FAMILIAR_MIN_SIZE / 2 * resize);
        isOccupied = false;
        this.minDie = minimumDie;
    }

    public void setupGestureTarget() {
        /* Setting up drag event handlers */
        setupDragOver();
        setupDragEntered();
        setupDragExited();
        setupDragDropped();
    }

    public void setupDragOver() {
        occupationCircle.setOnDragOver((DragEvent e) -> {
            /* source is dragged over the occupationCircle */

            if (e.getGestureSource() != occupationCircle && e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });
    }

    public void setupDragEntered() {
        occupationCircle.setOnDragEntered((DragEvent e) -> {

            if (!occupied && e.getGestureSource() != occupationCircle && e.getDragboard().hasImage()) {
                occupationCircle.setOpacity(0.4);
                occupationCircle.setFill(Color.FORESTGREEN);
            }

            //e.consume();
        });
    }

    public void setupDragExited() {
        occupationCircle.setOnDragExited((DragEvent e) -> {

            if(!occupied) {
                occupationCircle.setFill(Color.TRANSPARENT);
            }

            //e.consume();
        });
    }

    public void setupDragDropped() {
        occupationCircle.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;

            System.out.println("starting if");
            if(!occupied && e.getDragboard().hasImage()) {
                this.occupied = true;
                System.out.println("inside if");
                Image source = e.getDragboard().getImage();
                occupationCircle.setOpacity(1);
                occupationCircle.setFill(new ImagePattern(source));
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public void repaint() {
        if(occupied) {
            Image img = new Image(this.graphicMap.getFamiliarPath(this.occupantPlayerId, this.familyMemberId));
            occupationCircle.setOpacity(1);
            occupationCircle.setFill(new ImagePattern(img));
        }
        else {
            occupationCircle.setFill(Color.TRANSPARENT);
        }
    }

    public ColorEnumeration getFamilyMemberID() {
        return familyMemberId;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMinDie() {
        return minDie;
    }

    public void setMinDie(int minDie) {
        this.minDie = minDie;
    }

    public Circle getOccupationCircle() {
        return this.occupationCircle;
    }

    public void setOccupationCircle(Circle occupationCircle) {
        this.occupationCircle = occupationCircle;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ColorEnumeration getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(ColorEnumeration familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public ColorEnumeration getOccupantPlayerId() {
        return occupantPlayerId;
    }

    public void setOccupantPlayerId(ColorEnumeration occupantPlayerId) {
        this.occupantPlayerId = occupantPlayerId;
    }

}
