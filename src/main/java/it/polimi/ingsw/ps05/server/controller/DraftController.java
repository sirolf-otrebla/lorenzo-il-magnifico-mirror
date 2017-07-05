package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.cards.LeaderCard;
import it.polimi.ingsw.ps05.net.message.LeaderDraftUpdateNetMessage;
import it.polimi.ingsw.ps05.net.message.EndDraftMessage;
import it.polimi.ingsw.ps05.net.message.StartLeaderDraftMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;
import it.polimi.ingsw.ps05.utils.CommonJsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 02/07/2017.
 */
public class DraftController implements Runnable{

    private static final int MAX_LEADER_CARDS = 4;
    private CommonJsonParser parser;
    private Semaphore sem;
    private HashMap<ColorEnumeration, ArrayList<Integer>> leaderCardReferenceIdMatrix;
    private ArrayList<LeaderCard> leaderCardArrayList;
    private ArrayList<PlayerClient> clientArrayList;
    private HashMap<ColorEnumeration, ArrayList<Integer>> choosenCardsMap =  new HashMap<>();
    private HashMap<Integer, LeaderCard> leaderCardHashMap = new HashMap<>();

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

    private synchronized void sendInitialDraftMessage(){
        System.out.println("sending initial draft");
        try {
            sem.acquire(clientArrayList.size());
            for (int i = 0; i < this.clientArrayList.size(); i++){
                this.clientArrayList.get(i).sendMessage(
                        new StartLeaderDraftMessage(leaderCardReferenceIdMatrix.get(
                        this.clientArrayList.get(i).getPlayer().getColor())));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        assignRandomCards();
        sendInitialDraftMessage();
        for (int index = MAX_LEADER_CARDS; index > 0; index--) {
            try {
                sem.acquire(this.clientArrayList.size());
                for (int j = 0; j < this.clientArrayList.size(); j++){

                    ColorEnumeration thisPlayerColor = this.clientArrayList.get(j).getPlayer().getColor();
                    ArrayList<Integer> tempArrayList = new ArrayList<>();
                    ColorEnumeration nextPlayerColor;
                    if (j == this.clientArrayList.size() -1)
                        nextPlayerColor = this.clientArrayList.get(0).getPlayer().getColor();
                    else
                       nextPlayerColor = this.clientArrayList.get(j+1).getPlayer().getColor();
                    for (Integer i: this.leaderCardReferenceIdMatrix.get(nextPlayerColor))
                        tempArrayList.add(i);
                    this.leaderCardReferenceIdMatrix.remove(nextPlayerColor);
                    this.leaderCardReferenceIdMatrix.put(nextPlayerColor, tempArrayList);
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
        for (PlayerClient client : this.clientArrayList){
            Player player = client.getPlayer();
            ArrayList<Integer> playerCardIds = this.choosenCardsMap.get(player.getColor());
            EndDraftMessage message = new EndDraftMessage(playerCardIds);
            for (LeaderCard leader: this.leaderCardArrayList) {
                if (playerCardIds.contains(leader.getReferenceID())){
                    player.putLeaderCard(leader);
                }
            }
            client.sendMessage(message);
        }

    }

    public void DoChoice(ColorEnumeration playerColor, Integer leaderCardReferenceID){
        this.choosenCardsMap.get(playerColor).add(leaderCardReferenceID);
        this.leaderCardReferenceIdMatrix.get(playerColor).remove(leaderCardReferenceID);
        sem.release();
    }
}
