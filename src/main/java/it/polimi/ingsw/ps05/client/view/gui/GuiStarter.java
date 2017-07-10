package it.polimi.ingsw.ps05.client.view.gui;

import it.polimi.ingsw.ps05.client.ctrl.ViewAdapter;
import javafx.stage.Stage;

/**
 * Created by Alberto on 10/07/2017.
 */
public class GuiStarter implements Runnable {

    private GUIMain guiMain = new GUIMain();

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
