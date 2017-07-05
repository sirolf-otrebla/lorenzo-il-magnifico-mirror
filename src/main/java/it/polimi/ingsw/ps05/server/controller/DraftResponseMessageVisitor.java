package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.LeaderDraftChoiceMessage;
import it.polimi.ingsw.ps05.net.message.LeaderDraftUpdateNetMessage;
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
        this.client.getGame().getDraftController().DoChoice(
                client.getPlayer().getColor(), msg.getChoice());
    }

}
