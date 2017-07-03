package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.net.message.EnteringLobbyMessage;

/**
 * Created by Alberto on 03/07/2017.
 */
public class LobbyUpdatesVisitor {

    public  void visit(EnteringLobbyMessage message){
        Client.getInstance().getLoginController().setLobby();
        Client.getInstance().getLoginController().setLobbyUsernames(message.getPlayerUsernamesList());
    }
}
