package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import javafx.scene.shape.Circle;

/**
 * Created by Alberto on 27/06/2017.
 */
public class ActionSpaceWidget {

    public Circle familyMemberCircle;
    private int id;
    private int occupantPlayerID;
    private ColorEnumeration familyMemberID;

    public ColorEnumeration getFamilyMemberID() {
        return familyMemberID;
    }

    public int getOccupantID() {
        return occupantPlayerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { //TODO VERIFICARE SE È DA USARE QUESTO.
        this.id = id;
    }
}
