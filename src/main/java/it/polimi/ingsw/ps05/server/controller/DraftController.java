package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.net.message.draftmessages.LeaderDraftUpdateNetMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.LeaderDraftEndMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.StartLeaderDraftMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.utils.CommonJsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

/** This class takes care of the Leader Cards Draft when using advanced rules
 *
 * */
public class DraftController implements Runnable{

    private static final int MAX_LEADER_CARDS = 4;
    private CommonJsonParser parser;
    private Semaphore sem;
    private HashMap<ColorEnumeration, ArrayList<Integer>> leaderCardReferenceIdMatrix;
    private ArrayList<LeaderCard> leaderCardArrayList;
    private ArrayList<PlayerClient> clientArrayList;
    private HashMap<ColorEnumeration, ArrayList<Integer>> choosenCardsMap =  new HashMap<>();
    private HashMap<Integer, LeaderCard> leaderCardHashMap = new HashMap<>();

    /**
     * this is the DraftController constructor. it needs an Arraylist of connected clients,
     * which are the ones that participate to the Draft. moreover, it needs a reference to the game
     * @param clients this is an {@code ArrayList} of connected {@link PlayerClient}
     * @param game this is a reference to the game containing the leader draft.
     */
    public DraftController(ArrayList<PlayerClient> clients, Game game){
        this.leaderCardArrayList = game.getBoard().getLeaderCardsList();
        ArrayList<PlayerClient> draftClientArrayList = new ArrayList<>();
        for (PlayerClient client : clients) draftClientArrayList.add(client);
        leaderCardReferenceIdMatrix = new HashMap<>();
        Collections.shuffle(draftClientArrayList);
        for (PlayerClient client: draftClientArrayList) {
            leaderCardReferenceIdMatrix.put(client.getPlayer().getColor(), new ArrayList<>());
        }
        leaderCardArrayList = game.getBoard().getLeaderCardsList();
        for (LeaderCard leader: leaderCardArrayList) {
            leaderCardHashMap.put(leader.getReferenceID(), leader);
        }
        clientArrayList = draftClientArrayList;
        this.sem = new Semaphore(draftClientArrayList.size());
        for (PlayerClient client: draftClientArrayList) {
            choosenCardsMap.put(client.getPlayer().getColor(), new ArrayList<>());
        }


    }

    /** this method assigns random cards to every player connected
     *
     */
    private void assignRandomCards(){
        Random rand = new Random();
        for(ArrayList<Integer> array : leaderCardReferenceIdMatrix.values()){
            for(int i = 0; i < MAX_LEADER_CARDS; i ++){
                int cardIndex = rand.nextInt(this.leaderCardArrayList.size());
                array.add(this.leaderCardArrayList.get(cardIndex).getReferenceID());
                this.leaderCardArrayList.remove(cardIndex);
            }
        }
    }

    /**
     *  this method send a {@link StartLeaderDraftMessage} which warns the Client that a
     *  Leader Card Draft is starting. the StartLeaderDraftMessage also contains a random
     *  set of Leader Cards, which are the first one drafted.
     */
    private void sendInitialDraftMessage(){
        try {
        	System.out.println("Pre sem");
            sem.acquire(clientArrayList.size());
            System.out.println("sending initial draft");
            synchronized (this) {
            	for (int i = 0; i < this.clientArrayList.size(); i++){
            		this.clientArrayList.get(i).sendMessage(
                    new StartLeaderDraftMessage(leaderCardReferenceIdMatrix.get(
                            this.clientArrayList.get(i).getPlayer().getColor())));
                }
			}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /** this is the DraftController's main loop. as a Run method, it have to be executed
     * by a separate thread, which is synchronized with the Game's main thread. Actually, the
     * main thread waits this method to stops by calling a {@code  Thread.join()} on his thread.
     * This method is governed by a semaphore, which represents how many clients have already chosen their
     * leader cards. when the semaphore has many permits as the number of connected players, the loop proceeds.
     *
     */
    @Override
    public void run() {
        assignRandomCards();
        sendInitialDraftMessage();
        for (int index = MAX_LEADER_CARDS-1; index > 0; index--) {
        	System.out.println("ciclo delle carte leader. manca : " + index);
            try {
                sem.acquire(this.clientArrayList.size());
                ColorEnumeration thisPlayerColor = this.clientArrayList.get(0).getPlayer().getColor();
                ArrayList<Integer> thisPlayerList = this.leaderCardReferenceIdMatrix.get(thisPlayerColor);
                for (int j = 0; j < this.clientArrayList.size(); j++){
                    ArrayList<Integer> tempArrayList = new ArrayList<>();
                    ColorEnumeration nextPlayerColor;
                    if (j == this.clientArrayList.size() -1)
                        nextPlayerColor = this.clientArrayList.get(0).getPlayer().getColor();
                    else
                       nextPlayerColor = this.clientArrayList.get(j+1).getPlayer().getColor();
                    for (Integer i: this.leaderCardReferenceIdMatrix.get(nextPlayerColor))
                        tempArrayList.add(i);

                    this.leaderCardReferenceIdMatrix.remove(nextPlayerColor);
                    this.leaderCardReferenceIdMatrix.put(nextPlayerColor,
                            thisPlayerList);
                    thisPlayerList = tempArrayList;
                }
                for (PlayerClient client : this.clientArrayList){
                    LeaderDraftUpdateNetMessage message = new LeaderDraftUpdateNetMessage(this.leaderCardReferenceIdMatrix.get(
                            client.getPlayer().getColor()));
                    client.sendMessage(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fuori da ciclo carte leader");
        System.out.println("Sto per comunicare le carte scelte a tutti");
        for (PlayerClient client : this.clientArrayList){
            Player player = client.getPlayer();
            ArrayList<Integer> playerCardIds = this.choosenCardsMap.get(player.getColor());
            System.out.println("CARTE LEADER ALLA FINE DEL DRAFT: " + playerCardIds.size());
            System.out.println("Carte rimaste nella hashmap: " + leaderCardReferenceIdMatrix.get(player.getColor()).size());
            LeaderDraftEndMessage message = new LeaderDraftEndMessage(playerCardIds);
            for (Integer id : playerCardIds){
            	player.putLeaderCard(leaderCardHashMap.get(id));
            	System.out.println("Aggiungo carta leader a player " + player.getLeaderCardList().size());
            }
            player.putLeaderCard(leaderCardHashMap.get(leaderCardReferenceIdMatrix.get(player.getColor()).get(0)));
            client.sendMessage(message);
        }
        System.out.println("post comunicato carte");

    }

    /** this method is called by the {@link DraftResponseMessageVisitor} when a {@link it.polimi.ingsw.ps05.net.message.draftmessages.DraftResponseNetMessage}
     * is sent by a Client. here the choice is applied and the Main loop semaphore released.
     *
     * @param playerColor   the Choosing player's color
     * @param leaderCardReferenceID     the leaderReferenceID, which is static and unique for every Leader Card.
     */
    public void DoChoice(ColorEnumeration playerColor, Integer leaderCardReferenceID){
        this.choosenCardsMap.get(playerColor).add(leaderCardReferenceID);
        this.leaderCardReferenceIdMatrix.get(playerColor).remove(leaderCardReferenceID);
        System.out.println(this.leaderCardReferenceIdMatrix.get(playerColor).toString());
        sem.release();
        System.out.println("(DraftController) semaforo rilasciato: numero permessi: \t" +
                 + sem.availablePermits());
    }
}
