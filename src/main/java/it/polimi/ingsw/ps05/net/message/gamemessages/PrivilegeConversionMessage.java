package it.polimi.ingsw.ps05.net.message.gamemessages;

import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.controller.GameCommandsVisitor;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.ArrayList;

/**
 * Created by Alberto on 08/07/2017.
 */
public class PrivilegeConversionMessage implements NetMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6560428085612827867L;
	private ArrayList<Integer> choices;
    private Integer playerID;

    public PrivilegeConversionMessage(ArrayList<Integer> choices, Integer playerID) {
        this.choices = choices;
        this.playerID = playerID;
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }

    @Override
    public void acceptVisitor(NetMessageVisitor vi) throws Exception {
        vi.visit(this);
    }


    public Integer getPlayerID() {
        return playerID;
    }
}


