package it.polimi.ingsw.ps05.net.message.draftmessages;

import it.polimi.ingsw.ps05.client.ctrl.DraftVisitor;
import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 10/07/2017.
 */
public class StartBonusTileDraftMessage implements DraftMessage {

    public StartBonusTileDraftMessage(ArrayList<Integer> idArrayList) {
        IdArrayList = idArrayList;
    }
    private  ArrayList<Integer> IdArrayList = new ArrayList<>();


    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(DraftVisitor vi) {
        vi.visit(this);
    }

    public ArrayList<Integer> getIdArrayList() {
        return IdArrayList;
    }
}
