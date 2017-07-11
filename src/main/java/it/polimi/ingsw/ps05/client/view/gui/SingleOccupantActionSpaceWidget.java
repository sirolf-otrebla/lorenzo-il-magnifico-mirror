package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarData.FAMILIAR_DATA;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;


public class SingleOccupantActionSpaceWidget extends Observable implements ActionSpaceWidgetInterface {

    private Integer referenceId;
    private boolean occupied;
    private Circle occupationCircle;
    private ColorEnumeration familyMemberColor;
    private ColorEnumeration occupantPlayerColor;
    private boolean isOccupied;
    private int minDie;
    HashMap<ColorEnumeration, Boolean> legalActionMap = new HashMap<>();

    public SingleOccupantActionSpaceWidget(int minimumDie) {
        occupationCircle = new Circle(FAMILIAR_MIN_SIZE / 2 * resize);
        occupationCircle.setFill(Color.TRANSPARENT);
        isOccupied = false;
        this.minDie = minimumDie;
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
        occupationCircle.setOnDragOver((DragEvent e) -> {
            /* source is dragged over the occupationCircle */

            FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
            boolean isLegal = legalActionMap.get(sourceData.getFamiliarColor());
            ColorEnumeration plColor = sourceData.getPlayerColor(), fColor = sourceData.getFamiliarColor();

            System.out.println("Occupato: " + occupied);
            System.out.println("Azione legale: " + isLegal);
            System.out.println("Giocatore: " + plColor);
            System.out.println("Familiare: " + fColor);
            System.out.println("dragboard.hasContent(FAMILIAR_DATA): " + e.getDragboard().hasContent(FAMILIAR_DATA));
            if (!occupied && e.getGestureSource() != this && e.getDragboard().hasContent(FAMILIAR_DATA) && isLegal) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });
    }

    public void setupDragEntered() {
        occupationCircle.setOnDragEntered((DragEvent e) -> {

            System.out.println("DRAG ENTERED");

            FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
            boolean isLegal = legalActionMap.get(sourceData.getFamiliarColor());
            System.out.println("is legal: \t" + isLegal);
            System.out.println("dati ottenuti dalla dragboard");
            System.out.println("playercolor: " + sourceData.getPlayerColor());
            System.out.println("familiarcolor: " + sourceData.getFamiliarColor());
            System.out.println("imagepath: " + sourceData.getFamiliarImagePath());
            System.out.println("Occupato: " + occupied);
            System.out.println("e.getGestureSource() != this: " + (e.getGestureSource() != this));
            System.out.println("e.getDragboard().hasContent(FAMILIAR_DATA): " + e.getDragboard().hasContent(FAMILIAR_DATA));
            System.out.println("is legal: " + isLegal);

            if (!occupied && e.getGestureSource() != this && e.getDragboard().hasContent(FAMILIAR_DATA) && isLegal) {
                occupationCircle.setOpacity(0.4);
                occupationCircle.setFill(Color.FORESTGREEN);
            } else if (!occupied && e.getGestureSource() != this && e.getDragboard().hasContent(FAMILIAR_DATA) && !isLegal){
                occupationCircle.setOpacity(0.4);
                occupationCircle.setFill(Color.FIREBRICK);
            }

            //e.consume();
        });
    }

    public void setupDragExited() {
        occupationCircle.setOnDragExited((DragEvent e) -> {

            if (!occupied) {
                occupationCircle.setFill(Color.TRANSPARENT);
            }

            //e.consume();
        });
    }

    public void setupDragDropped() {

        occupationCircle.setOnDragDropped((DragEvent e) -> {
            /* What to do when the source is dropped */
            boolean success = false;
            FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
            boolean isLegal = legalActionMap.get(sourceData.getFamiliarColor());

            System.out.println("starting if");
            if (!isOccupied() && isLegal && e.getDragboard().hasContent(FAMILIAR_DATA)) {
                Image img = new Image(sourceData.getFamiliarImagePath());
                System.out.println("inside action space drag dropped");
                occupationCircle.setOpacity(1);
                occupationCircle.setFill(new ImagePattern(img));
                this.occupied = true;
                this.setFamilyMemberColor(sourceData.getFamiliarColor());
                this.setOccupantPlayerColor(sourceData.getPlayerColor());
                success = true;
            }

            e.setDropCompleted(success);

            //e.consume();
        });
    }

    public void repaint() {
        if (occupied) {
            Image img = new Image(GraphicResources.getFamiliarPath(this.occupantPlayerColor, this.familyMemberColor));
            occupationCircle.setOpacity(1);
            occupationCircle.setFill(new ImagePattern(img));
        } else {
            occupationCircle.setFill(Color.TRANSPARENT);
        }
    }

    public ColorEnumeration getFamilyMemberID() {
        return familyMemberColor;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
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

    public ColorEnumeration getFamilyMemberColor() {
        return familyMemberColor;
    }

    public void setFamilyMemberColor(ColorEnumeration familyMemberColor) {
        this.familyMemberColor = familyMemberColor;
    }

    public ColorEnumeration getOccupantPlayerColor() {
        return occupantPlayerColor;
    }

    public void setOccupantPlayerColor(ColorEnumeration occupantPlayerColor) {
        this.occupantPlayerColor = occupantPlayerColor;
    }


    public void setLegalActionMap(HashMap<ColorEnumeration, Boolean> legalActionMap) {
        this.legalActionMap = legalActionMap;
    }

    public HashMap<ColorEnumeration, Boolean> getLegalActionMap() {
        return legalActionMap;
    }
}
