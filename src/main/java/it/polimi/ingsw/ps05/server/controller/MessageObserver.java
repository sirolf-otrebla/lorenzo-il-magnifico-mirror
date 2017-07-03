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
public class MessageObserver implements Observer, NetMessageVisitor {

    private PlayerClient client;
    private static MessageObserver instance = null;
    private MessageObserver(){
        //TODO
    }

    public static MessageObserver getInstance(){
        if (instance ==  null) instance = new MessageObserver();
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

    public void visit(GameMessage msg){
        if (this.client.isInGame() == false){
        }
        else {
            this.client.getGame().getState().setInputMessage(msg);
        }
    }

    public void visit(LobbyMessage msg){
        if (this.client.isInGame()){
            //TODO
        }

    }

    @Override
    public void visit(LeaderDraftMessage msg) {

    }

    @Override
    public void visit(AuthMessage msg) {
        AuthListener listener = new AuthListener(this.client);
        msg.acceptVisitor(listener);
    }

    @Override
    public void visit(AuthResponseMessage msg) {
        //todo
    }
}
