package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.*;
import it.polimi.ingsw.ps05.model.cards.GreenCard;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.model.cards.YellowCard;
import it.polimi.ingsw.ps05.model.exceptions.MissingCardException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tower;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.message.gamemessages.*;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.ActivateLeaderCardStrategy;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.ClaimPrivilegeStrategy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is meant to select and compute messages from Clients that are connected to a certain game
 * session. it uses the Visitor Pattern and applies the result of the computation to a {@link Round} object,
 * which represents the state of the game flow/
 * @see Round
 * @see GameFlowController
 * @see GameMessage
 */
public class GameCommandsVisitor implements VisitorInterface {

    private Player activePlayer;
    private Round round;


    /**
     *  main constructor for GameCommandsVisitor
     * @param activePlayer represents the active player (the player that can send and receive
     *                    messages from server
     * @param round represents the game flow internal state
     */
    public GameCommandsVisitor(Player activePlayer, Round round) {
        this.activePlayer = activePlayer;
        this.round = round;
    }

    /** this method is called when a {@link HarvestActionMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param msg represents the message sent by the client.
     */
    public void visit(HarvestActionMessage msg){
        HashMap<Integer, GreenCard> map = this.activePlayer.getGreenCardHashMap();
        for (Integer i: msg.getActiveCardsIds() )
            map.get(i).setToBeActivated(true);
        msg.getActionMessage().acceptVisitor(this);
        for (Integer i: msg.getActiveCardsIds() )
            map.get(i).setToBeActivated(false);

    }
    /** this method is called when a {@link ProductionActionMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param msg represents the message sent by the client.
     */
    public void visit(ProductionActionMessage msg){
        HashMap<Integer, YellowCard> map = this.activePlayer.getYellowCardHashMap();
        for (int i = 0; i < msg.getActiveCardsIds().size(); i++){
        	map.get(msg.getActiveCardsIds().get(i)).setToBeActivated(true);
        	map.get(msg.getActiveCardsIds().get(i)).setSelectedEffects(msg.optionForCard().get(i));
        	
        }
        
        msg.getActionMessage().acceptVisitor(this);

        for (int i = 0; i < msg.getActiveCardsIds().size(); i++) {
            map.get(msg.getActiveCardsIds().get(i)).setToBeActivated(false);
        }
    }
    /** this method is called when a {@link DiscardLeaderMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param msg represents the message sent by the client.
     */
    public void visit(DiscardLeaderMessage msg){
        Integer leaderCardReferenceID = msg.getLeaderCardReferenceID();
        this.activePlayer.getLeaderCardHashMap().remove(leaderCardReferenceID);
        this.round.getGame().getEndActionStrategyContainer().setChosenStrategy(new ClaimPrivilegeStrategy(1));
        //TODO
    }

    /** this method is called when a {@link PassActionMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param msg represents the message sent by the client.
     */
    public void visit(PassActionMessage msg){
        System.out.println("action passed");
    }

    /** this method is called when a {@link ActivateLeaderMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param lCardMsg represents the message sent by the client.
     */
    public void visit(ActivateLeaderMessage lCardMsg){
            LeaderCard card = this.activePlayer.getLeaderCardHashMap().get(lCardMsg.getLeaderCardReferenceID());
            card.applyNonActivableEffects(activePlayer);
            this.round.getGame().getEndActionStrategyContainer().setChosenStrategy(new ActivateLeaderCardStrategy());

    }

    /** this method is called when a {@link ActionMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * An actionmessage is the most important type of Game message, because of being decorated by
     * several other Game messages.
     * @param mess represents the message sent by the client.
     */
    @Override
    public void visit(ActionMessage mess) {
    	System.out.println("visiting action message in game commands");
        try {
            System.out.println("ActionMessage visit method for: ");
            System.out.println("__________________________________________________");
            System.out.println(mess);
            System.out.println("__________________________________________________");
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
            if (mess.getFamiliarID().equals(ColorEnumeration.Ghost)) activePlayer.removeGhostFamiliar();

           // for (PlayerClient client :
                   //TODO: round.getGame().getPlayerInGame()) {
          //      client.sendMessage(updateMsg);
           // }
        } catch (Exception e){
            e.printStackTrace();
        }


    }
    /** this method is called when a {@link ExitGameMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param mess represents the message sent by the client. In this implementation it is not
     *             necessary to catch this message because of the network-side classes that are
     *             already handling this situation.
     */
    @Override
    public void visit(ExitGameMessage mess){
        //TODO:
        // gestione permanenza partita
    }

    /** this method is called when a {@link PrivilegeConversionMessage} is sent by the client.
     * the goal of this method is to handle de client request and modify the round status.
     * @param msg represents the message sent by the client.
     */
    public void visit(PrivilegeConversionMessage msg){
        ArrayList<Integer> choices = msg.getChoices();
        Game game = this.round.getGame();
        ArrayList<ArrayList<Resource>> alternatives = game.getPrivilegeConvResAlternatives();
        for (Integer i : choices) {
            for (Resource r: alternatives.get(i)) {
                activePlayer.addResource(r);
            }
        }
        System.out.println("strategy Ended");
        game.getEndActionStrategyContainer().strategyEnded();
    }


    /** this method is an auxiliary method designed to validate the player class, who's sent
     * by the client in certain messages who needs player validation. in this way, the software
     * is aware of possible manipulations and hacks/cheats on the client code.
     * @param expected
     * @throws Exception
     */
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
