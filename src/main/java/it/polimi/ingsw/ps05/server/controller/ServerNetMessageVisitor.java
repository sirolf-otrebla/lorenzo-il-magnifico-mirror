package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.net.message.draftmessages.DraftMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.DraftResponseNetMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.ExcommunicationChoiceMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameResponseMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.PrivilegeConversionMessage;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ServerNetMessageVisitor implements Observer, NetMessageVisitor {

    private PlayerClient client;


    @Override
    public void update(Observable o, Object arg) {
        this.client = (PlayerClient) o;
        NetMessage msg = (NetMessage) arg;
        System.out.println("lo ha mandato " + client.getUsername());
        try {
            System.out.println("-------------------------");
            System.out.println(msg);
            System.out.println("_________________________");
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
            System.out.println("client is not in game");
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
    public void visit(ExcommunicationChoiceMessage msg) {
        if (msg.isAcceptExcommunication()){
            Player p = client.getPlayer();
            msg.getExcommunicationCard().applyEffect(p);
        }
    }

    @Override
    public void visit(PrivilegeConversionMessage msg) {
        Player activePlayer = this.client.getGame().getPlayerClient(msg.getPlayerID()).getPlayer();
        ArrayList<Integer> choices = msg.getChoices();
        Game game = this.client.getGame();
        ArrayList<ArrayList<Resource>> alternatives = game.getPrivilegeConvResAlternatives();
        for (Integer i : choices) {
            for (Resource r: alternatives.get(i)) {
                activePlayer.addResource(r);
            }
        }
        System.out.println("strategy Ended");
        game.getEndActionStrategyContainer().strategyEnded();
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
