package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftEndMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftUpdateNetMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.StartBonusTileDraftMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 10/07/2017.
 */
public class BonusTileDraftController implements Runnable {

    private Semaphore sem;
    private HashMap<Integer, BonusTile> bonusTileHashMap = new HashMap<>();
    private ArrayList<BonusTile> bonusTileArrayList;
    private ArrayList<PlayerClient> playerClients;
    private ArrayList<Integer> bonusTilesIds;
    private PlayerClient activeClient;


    public BonusTileDraftController(ArrayList<PlayerClient> clients, Game game){
        bonusTileArrayList = game.getBoard().getBonusTileArrayList();
        bonusTilesIds = new ArrayList<>();
        playerClients = clients;
        for (BonusTile tile: bonusTileArrayList) {
            bonusTileHashMap.put(tile.getReferenceID(), tile);
        }
        sem = new Semaphore(0);
    }

    public void sendInitialDraftMessage(){
        for (BonusTile bonusT: bonusTileArrayList) {
            bonusTilesIds.add(bonusT.getReferenceID());
        }
        for (PlayerClient client: playerClients) {
           client.sendMessage( new StartBonusTileDraftMessage(bonusTilesIds));
        }
    }

    public void setChoice(Integer choice){
        this.activeClient.getPlayer().setBonusTile(bonusTileHashMap.get(choice));
        this.bonusTileArrayList.remove(bonusTileHashMap.get(choice));
        bonusTilesIds.clear();
        for (BonusTile bonusT: bonusTileArrayList) {
            bonusTilesIds.add(bonusT.getReferenceID());
        }
        sem.release();
    }


    @Override
    public void run() {
        sendInitialDraftMessage();
        for (PlayerClient client : this.playerClients) {
            activeClient = client;
            activeClient.sendMessage(new BonusTileDraftUpdateNetMessage(bonusTilesIds));
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        for (PlayerClient client: this.playerClients) {
            client.sendMessage(new BonusTileDraftEndMessage());

        }
    }
}
