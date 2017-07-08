package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 08/07/2017.
 */
public class PrivilegeConversionMessage implements GameMessage {

    private ArrayList<Integer> choices;

    public PrivilegeConversionMessage(ArrayList<Integer> choices) {
        this.choices = choices;
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }

    @Override
    public void acceptVisitor(GameCommandsVisitor vi) {
        vi.visit(this);
    }
}
