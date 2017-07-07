package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ServerNetMessageVisitor implements Observer, NetMessageVisitor {

    private PlayerClient client;
    private static ServerNetMessageVisitor instance = null;
    private ServerNetMessageVisitor(){
        //TODO
    }

    public static ServerNetMessageVisitor getInstance(){
        if (instance ==  null) instance = new ServerNetMessageVisitor();
        return instance;
    }
    @Override
    public void update(Observable o, Object arg) {
        this.client = (PlayerClient) o;
        NetMessage msg = (NetMessage) arg;
        try {
            msg.acceptVisitor(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(GameResponseMessage msg) {
        // NO
    }

    public void visit(GameMessage msg){
        if (this.client.isInGame() == false){
            //TODO
        }
        else {
            this.client.getGame().getState().setInputMessage(msg);
        }
    }

    @Override
    public void visit(LobbyMessage msg) {

    }

    public void visit(LobbyResponseMessage msg){
        System.out.println("LobbyResponseMessage received");
       LobbyResponseMessageVisitor visitor  = new LobbyResponseMessageVisitor(this.client);
       msg.acceptVisitor(visitor);
    }

    @Override
    public void visit(DraftMessage msg) {

    }

    @Override
    public void visit(DraftResponseNetMessage msg) {
        System.out.println("(DraftResponseNetMessage primo visitor");
        DraftResponseMessageVisitor visitor =
                new DraftResponseMessageVisitor(this.client);
        msg.acceptVIsitor(visitor);
    }


    @Override
    public void visit(AuthMessage msg) {
        AuthListener listener = new AuthListener(this.client);
        System.out.println("messaggio di auth a ServerNetMessageVisitor");
        msg.acceptVisitor(listener);
    }

    @Override
    public void visit(AuthResponseMessage msg) {
        //todo
    }

}
