package it.polimi.ingsw.ps05.server.controller;


import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.net.message.SetupDoneMessage;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
/**
 * this class represent the lobby response message visitor, which at this moment consists of a single method,
 * that handle {@link SetupDoneMessage} messages; the main goal of this class is to release the {@link Game}
 * so he can start when all Client has completed the setup.

 */
public class LobbyResponseMessageVisitor {

    public PlayerClient client;
    public LobbyResponseMessageVisitor(PlayerClient client){
        this.client = client;
    }

    public void visit(SetupDoneMessage msg){
        System.out.println("Setup done, CLIENT NAME" + client.getUsername());
        client.getGame().getSemStart().release();
    }

}
