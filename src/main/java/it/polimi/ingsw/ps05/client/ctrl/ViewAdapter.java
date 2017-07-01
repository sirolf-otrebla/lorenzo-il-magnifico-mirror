package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.view.LimView;
import it.polimi.ingsw.ps05.client.view.cli.CLIMain;
import it.polimi.ingsw.ps05.client.view.gui.GUIMain;
import it.polimi.ingsw.ps05.client.view.gui.TowerTileWidget;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.exceptions.IllegalActionException;
import it.polimi.ingsw.ps05.model.spaces.ActionSpace;
import it.polimi.ingsw.ps05.model.spaces.Tile;
import it.polimi.ingsw.ps05.model.spaces.TowerTileInterface;
import it.polimi.ingsw.ps05.net.GameStatus;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alberto on 29/06/2017.
 */
public class ViewAdapter {
    public static final String GUI_TYPE = "gui";
    public static final String CLI_TYPE = "cli";

    private String viewType;

    private LimView view;

    private static ViewAdapter instance = null;

    private ViewAdapter(String type){
        if( type.toLowerCase() == this.GUI_TYPE){
            viewType = type.toLowerCase();
            // TODO ISTANZIARE GUI
        }

        else if(type.toLowerCase() == ViewAdapter.CLI_TYPE) {
            viewType = type.toLowerCase();

            //TODO ISTANZIARE CLI
        }
    }

    public static ViewAdapter getInstance() throws NullPointerException{
        if (instance != null) return instance;
        else throw new NullPointerException();
    }
    public static ViewAdapter createInstance(String type) throws IllegalAccessException{
        if (instance != null) throw new IllegalAccessException();
        return  new ViewAdapter(type);
    }

    private void setUpGui(GameStatus gameStatus){
        GUIMain gui = new GUIMain();

        UpdateViewVisitor guiVisitor = new  UpdateViewVisitor(Client.getInstance(), gui);

        //TODO
    }

    private void setUpCli(GameStatus gameStatus){
        ArrayList<Player> playerArrayList = new ArrayList<>(gameStatus.getPlayerHashMap().values());
        this.view = new CLIMain(gameStatus.getGameBoard(), gameStatus.getThisPlayer(),playerArrayList);
    }

    private void updateView(){


    }


}
