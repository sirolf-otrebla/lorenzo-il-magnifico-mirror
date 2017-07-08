package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Action;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.message.gamemessages.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 19/06/2017.
 */
public class GameCommandsVisitor implements VisitorInterface {

    private Player activePlayer;
    private Round round;


    public GameCommandsVisitor(Player activePlayer, Round round) {
        this.activePlayer = activePlayer;
        this.round = round;
    }

    public void visit(HarvestActionMessage msg){
        HashMap<Integer, GreenCard> map = this.activePlayer.getGreenCardHashMap();
        for (Integer i: msg.getActiveCardsIds() )
            map.get(i).setToBeActivated(true);
        msg.getActionMessage().acceptVisitor(this);

    }

    public void visit(ProductionActionMessage msg){
        HashMap<Integer, YellowCard> map = this.activePlayer.getYellowCardHashMap();
        for (Integer i : msg.getActiveCardsIds())
            map.get(i).setToBeActivated(true);
        msg.getActionMessage().acceptVisitor(this);
    }

    public void visit(DiscardLeaderMessage msg){

        //TODO
    }

    public void visit(PassActionMessage msg){
        //DO NOTHING ?
    }
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
            Integer actionSpaceID = mess.getActionSpaceID();
            Board board = this.round.getGame().getBoard();
            ActionSpace actionSpace = null;

            if (board.getActSpacesMap()
                    .keySet().contains(actionSpaceID)){
                // siamo in un ActionSpace
                actionSpace = board.getActSpacesMap().get(actionSpaceID);
            } else {
                // siamo in una Torre
                for (Tower tower:
                   board.getTowerList().values()) {
                    if (tower.getTiles().keySet().contains(actionSpaceID))
                       actionSpace =  tower.getTiles().get(actionSpaceID);
                }
            }
            System.out.println("familiare: " + this.activePlayer.getFamilyMap().get(mess.getFamiliarID()) + "\n"+
            "action space: " + actionSpace + "\n"+
            		"payment: " + mess.getSelectedPayment());
            Action act = thisPl.doAction(
                    this.activePlayer.getFamilyMap().get(mess.getFamiliarID()), actionSpace,
                    mess.getSelectedPayment());

           // for (PlayerClient client :
                   //TODO: round.getGame().getPlayerInGame()) {
          //      client.sendMessage(updateMsg);
           // }
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void visit(ExitGameMessage mess){
        //TODO:
        // gestione permanenza partita
    }


    public void visit(PrivilegeConversionMessage msg){
        ArrayList<Integer> choices = msg.getChoices();
        Game game = this.round.getGame();
        ArrayList<ArrayList<Resource>> alternatives = game.getPrivilegeConvResAlternatives();
        for (Integer i : choices) {
            for (Resource r: alternatives.get(i)) {
                activePlayer.addResource(r);
            }
        }
        game.getEndActionStrategyContainer().strategyEnded();
    }

    private void validatePlayer(Player expected) throws  Exception{
        ArrayList<Resource> resList = expected.getResourceList();
        for (Resource res: resList) {
            if(!(activePlayer.getResource(res.getID()).getValue().equals(res.getValue()))){
                System.out.println("risorsa " + res + " di valore " + res.getValue());
                System.out.println("Diversa da " + activePlayer.getResource(res.getID())
                + "di valore " + activePlayer.getResource(res.getID()).getValue() );
                throw new Exception();
            }
        }
    }
}
