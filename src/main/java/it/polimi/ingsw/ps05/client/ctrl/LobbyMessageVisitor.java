package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.EnteringLobbyMessage;

import java.util.ArrayList;

/**
 * Created by Alberto on 03/07/2017.
 */
public class LobbyMessageVisitor {

    public LobbyMessageVisitor(){


    }

    public void visit(EnteringLobbyMessage lobbyMessage){
        if (lobbyMessage.isYouOrOthers()) ;
        System.out.println("sono nel secondo visitor");
        ArrayList<String> usernames = lobbyMessage.getPlayerUsernamesList();
        Client.getInstance().getLoginController().setLobby();
        Client.getInstance().getLoginController().setLobbyUsernames(lobbyMessage.getPlayerUsernamesList());
    }
}
