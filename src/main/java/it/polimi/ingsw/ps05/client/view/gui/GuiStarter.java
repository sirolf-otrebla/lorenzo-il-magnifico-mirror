package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.client.ctrl.ViewAdapter;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.server.controller.GameSetup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 10/07/2017.
 */
public class GuiStarter implements Runnable {

    private GUIMain guiMain = new GUIMain();

    public GuiStarter(GameStatus status) {
        Player activePlayer = status.getPlayerHashMap().get(status.getActivePlayerId());
        status.getPlayerHashMap().remove(status.getThisPlayer().getPlayerID());
        ArrayList<Player> playerArrayList = new ArrayList< >(status.getPlayerHashMap().values());
        HashMap<ColorEnumeration, String> usernamesHashMap = new HashMap<>();
        for (Player p: status.getPlayerHashMap().values()) usernamesHashMap.put(p.getColor(), p.getUsername());
        this.guiMain.setInitValues(status.getThisPlayer().getColor(), status.getPlayerHashMap().size(), 120, usernamesHashMap);
    }

    @Override
    public void run() {
        try {
            guiMain.start(new Stage());
            ViewAdapter.getInstance().getGuiInitSemaphore().release();
            System.out.println("SEMAPHORE RELEASED, GUI INITIALIZED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GUIMain getGuiMain() {
        return guiMain;
    }
}
