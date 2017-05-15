package it.polimi.ingsw.ps05.model;

/* todo: add methods defined in UML  --Sirolfo*/

import java.util.ArrayList;

public class CouncilSpace extends ActionSpaceWithEffect {

    private ArrayList<Familiar> occupantList;

    @Override
    public void setOccupied(Familiar occupant){
        occupantList.add(occupant);
    }


}
