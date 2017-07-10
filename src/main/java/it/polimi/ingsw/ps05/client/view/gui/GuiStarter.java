package it.polimi.ingsw.ps05.client.view.gui;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GUIMain getGuiMain() {
        return guiMain;
    }
}
