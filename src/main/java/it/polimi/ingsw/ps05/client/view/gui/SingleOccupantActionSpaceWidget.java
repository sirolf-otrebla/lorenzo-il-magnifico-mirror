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

import static it.polimi.ingsw.ps05.client.view.gui.FamiliarData.FAMILIAR_DATA;
import static it.polimi.ingsw.ps05.client.view.gui.GUIMain.*;


public class SingleOccupantActionSpaceWidget implements ActionSpaceWidgetInterface {

    private Integer referenceId;
    private boolean occupied;
    private Circle occupationCircle;
    private ColorEnumeration familyMemberColor;
    private ColorEnumeration occupantPlayerColor;
    private boolean isOccupied;
    private int minDie;
    private HashMap<ColorEnumeration, Boolean> legalActionMap = new HashMap<>();
    private ArrayList<ColorEnumeration> legalFamilyMemberList = new ArrayList<>();

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

            if (!occupied && e.getGestureSource() != this && e.getDragboard().hasContent(FAMILIAR_DATA) && isLegal) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            //e.consume();
        });
    }

    public void setupDragEntered() {
        occupationCircle.setOnDragEntered((DragEvent e) -> {

            FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
            boolean isLegal = legalActionMap.get(sourceData.getFamiliarColor());

            if (isLegal) {
                occupationCircle.setOpacity(0.4);
                occupationCircle.setFill(Color.FORESTGREEN);
            } else if (!isLegal){
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

            System.out.println("starting if");
            if (e.getDragboard().hasContent(FAMILIAR_DATA)) {
                FamiliarData sourceData = (FamiliarData)e.getDragboard().getContent(FAMILIAR_DATA);
                Image img = new Image(sourceData.getFamiliarImagePath());
                System.out.println("inside action space drag dropped");
                occupationCircle.setOpacity(1);
                occupationCircle.setFill(new ImagePattern(img));
                this.occupied = true;
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

    public ArrayList<ColorEnumeration> getLegalFamilyMemberList() {
        return legalFamilyMemberList;
    }

    public void setLegalFamilyMemberList(ArrayList<ColorEnumeration> legalFamilyMemberList) {
        this.legalFamilyMemberList = legalFamilyMemberList;
    }

    public void setLegalActionMap(HashMap<ColorEnumeration, Boolean> legalActionMap) {
        this.legalActionMap = legalActionMap;
    }

    public HashMap<ColorEnumeration, Boolean> getLegalActionMap() {
        return legalActionMap;
    }
}
