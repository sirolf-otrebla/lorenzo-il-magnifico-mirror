package it.polimi.ingsw.ps05.controller;

import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.LeaderCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.net.message.ActionMessage;
import it.polimi.ingsw.ps05.net.message.ExitGameMessage;
import it.polimi.ingsw.ps05.net.message.LeaderCardMessage;
import it.polimi.ingsw.ps05.net.message.UpdateMessage;
import it.polimi.ingsw.ps05.net.server.PlayerClient;
import it.polimi.ingsw.ps05.resourcesandbonuses.Resource;

import java.util.ArrayList;

/**
 * Created by Alberto on 19/06/2017.
 */
public class GameCommandsVisitor implements VisitorInterface {

    private Player activePlayer;
    private Round round;



    public void visit(LeaderCardMessage lCardMsg){
        try {
            LeaderCard card =
                    this.activePlayer.getLeaderCard(lCardMsg.getLeaderCard().getName());
            if (lCardMsg.getMsgType() == LeaderCardMessage.TYPE_DISCARD)
                card.discard(this.activePlayer);
            else{
                card.applyNonActivableEffects(activePlayer, lCardMsg.getEffectChoice());
                this.activePlayer.resetPermanentEffects();

            }
        } catch (MissingCardException e){
            //TODO:
        }
    }

    @Override
    public void visit(ActionMessage mess) {
        try {
            Player pl = mess.getPlayerBefore();
            validatePlayer(pl);
            Player thisPl = this.activePlayer;
            Action act = thisPl.doAction(mess.getFamiliar(),
                    mess.getActionSpace(), mess.getSelectedPayment());
            UpdateMessage updateMsg = new UpdateMessage(act, thisPl, this.activePlayer);
            for (PlayerClient client :
                    round.getGame().getPlayerInGame()) {
                client.sendMessage(updateMsg);
            }
        } catch (Exception e){
            //TODO:
        }


    }

    @Override
    public void visit(ExitGameMessage mess){
        //TODO:
        // gestione permanenza partita
    }

    private void validatePlayer(Player expected) throws  Exception{
        ArrayList<Resource> resList = expected.getResourceList();
        for (Resource res: resList) {
            if(activePlayer.getResource(res.getID()).getValue() != res.getValue())
                throw new Exception();
        }
    }
}
