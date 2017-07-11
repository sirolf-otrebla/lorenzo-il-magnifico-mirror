package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftChoiceMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.LeaderDraftChoiceMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

/** This class is a visitor class designed to take care of messages for the Leader Draft Controller and
 * BonusTileDraftController
 * @see DraftController
 * @see BonusTileDraftController
 */
public class DraftResponseMessageVisitor {
    private PlayerClient client;

    /** main constructor. it needs a reference of the {@link PlayerClient} who's sending the
     * message. it is instantiated by {@link ServerNetMessageVisitor}, which has access to every
     * connected player.
     * @param client    the client who's sending the message
     *
     * @see DraftController
     * @see PlayerClient
     * @see it.polimi.ingsw.ps05.server.net.NetMessageVisitor
     * @see it.polimi.ingsw.ps05.net.message.draftmessages.DraftResponseNetMessage
     */
    public DraftResponseMessageVisitor(PlayerClient client){
        this.client = client;
    }

    /** this method is called when a client sends a {@link LeaderDraftChoiceMessage}, which means
     *  that the client has chosen a Leader Card
     * @param msg   {@link LeaderDraftChoiceMessage} containing draft informations
     */
    public void visit(LeaderDraftChoiceMessage msg) {
        System.out.println("(LeaderDraftChoiceMessage) secondo visitor");
        this.client.getGame().getDraftController().DoChoice(
                client.getPlayer().getColor(), msg.getChoice());
    }

    /** this method is called when a client choose his Bonus tile, via {@link BonusTileDraftChoiceMessage}
     * message
     * @param msg   {@link BonusTileDraftChoiceMessage} containing draft informations
     */
    public void visit(BonusTileDraftChoiceMessage msg){
        System.out.println("(BonusTileDraftChoiceMessage) secondo visitor");
        this.client.getGame().getBonusTileDraftController().setChoice(msg.getChoice());
    }

}
