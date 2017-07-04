package it.polimi.ingsw.ps05.server.controller;


import it.polimi.ingsw.ps05.net.message.SetupDoneMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
/**
 * Created by Alberto on 13/06/2017.
 */
public class LobbyResponseMessageVisitor {

    public PlayerClient client;
    public LobbyResponseMessageVisitor(PlayerClient client){
        this.client = client;
    }

    public void visit(SetupDoneMessage msg){
        System.out.println("Setup done");
        client.getGame().getSemStart().release();
    }

}