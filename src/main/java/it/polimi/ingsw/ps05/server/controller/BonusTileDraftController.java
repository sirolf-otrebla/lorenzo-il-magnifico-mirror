package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftEndMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.BonusTileDraftUpdateNetMessage;
import it.polimi.ingsw.ps05.net.message.draftmessages.StartBonusTileDraftMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

/** this class takes care of managing the BonusTiles Draft. it implements the {@link Runnable}
 *  interface, thus is designed for being hosted by a separate thread.
 */
public class BonusTileDraftController implements Runnable {

    private Semaphore sem;
    private HashMap<Integer, BonusTile> bonusTileHashMap = new HashMap<>();
    private ArrayList<BonusTile> bonusTileArrayList;
    private ArrayList<PlayerClient> playerClients;
    private ArrayList<Integer> bonusTilesIds;
    private PlayerClient activeClient;

    /** this is the class constructor. it requires the clients connected to a single game, and the
     * related game reference
     *
     * @param clients  clients connected to a single game
     * @param game     related game reference
     */
    public BonusTileDraftController(ArrayList<PlayerClient> clients, Game game){
        bonusTileArrayList = game.getBoard().getBonusTileArrayList();
        bonusTilesIds = new ArrayList<>();
        playerClients = clients;
        for (BonusTile tile: bonusTileArrayList) {
            bonusTileHashMap.put(tile.getReferenceID(), tile);
        }
        sem = new Semaphore(0);
    }

    /**
     *  this method send the initial draft message, which starts the Draft procedure.
     */
    public void sendInitialDraftMessage(){
        for (BonusTile bonusT: bonusTileArrayList) {
            bonusTilesIds.add(bonusT.getReferenceID());
        }
        for (PlayerClient client: playerClients) {
           client.sendMessage( new StartBonusTileDraftMessage(bonusTilesIds));
        }
    }

    /**
     *  this method is called when a client choices a bonus tile, and send to the server
     *  the related message, which is treated by {@link DraftResponseMessageVisitor}
     * @param choice the integer representing the referenceID of the choosen bonus tile
     */
    public void setChoice(Integer choice){
        this.activeClient.getPlayer().setBonusTile(bonusTileHashMap.get(choice));
        this.bonusTileArrayList.remove(bonusTileHashMap.get(choice));
        bonusTilesIds.clear();
        for (BonusTile bonusT: bonusTileArrayList) {
            bonusTilesIds.add(bonusT.getReferenceID());
        }
        sem.release();
    }


    /**
     * this is the Class main loop, which is governed by a semaphore, released only when a certain player
     * makes a choice. the loop ends when all bonus tiles are assigned.
     *
     */
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
