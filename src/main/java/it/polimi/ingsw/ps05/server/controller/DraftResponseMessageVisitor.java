package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftChoiceMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.LeaderDraftChoiceMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

/**
 * Created by Alberto on 05/07/2017.
 */
public class DraftResponseMessageVisitor {
    private PlayerClient client;

    public DraftResponseMessageVisitor(PlayerClient client){
        this.client = client;
    }


    public void visit(LeaderDraftChoiceMessage msg) {
        System.out.println("(LeaderDraftChoiceMessage) secondo visitor");
        this.client.getGame().getDraftController().DoChoice(
                client.getPlayer().getColor(), msg.getChoice());
    }

    public void visit(BonusTileDraftChoiceMessage msg){
        System.out.println("(BonusTileDraftChoiceMessage) secondo visitor");
        this.client.getGame().getBonusTileDraftController().setChoice(msg.getChoice());
    }

}
