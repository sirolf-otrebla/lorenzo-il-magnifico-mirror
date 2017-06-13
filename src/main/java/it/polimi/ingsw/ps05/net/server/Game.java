package it.polimi.ingsw.ps05.net.server;

import it.polimi.ingsw.ps05.controller.GameFlowController;
import it.polimi.ingsw.ps05.controller.GameSetup;
import it.polimi.ingsw.ps05.controller.TurnSetupManager;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Game implements Observer {

    private int id;
    private GameSetup setup;
    private  GameFlowController flowCtrl;
    public boolean end;
    private Thread flowCrlThread;
    private TurnSetupManager tManager;
    private ArrayList<PlayerClient> list;
    private Board gBoard;

    private boolean useCompleteRules = true;
    private boolean useCustomBonusTiles = false;


    public Game(boolean useCompleteRules, boolean useCustomBonusTiles, int id){
        this.id = id;
        this.useCompleteRules = useCompleteRules;
        this.useCustomBonusTiles = useCustomBonusTiles;
        this.list = new ArrayList<>();

    }

    public void start(){

        this.flowCtrl = new GameFlowController(this);
        this.flowCrlThread = new Thread(flowCtrl);
        this.gBoard = this.setup.getBoard();
        tManager = this.setup.getTurnSetup();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).BuildPlayer(ColorEnumeration.values()[i]);
            players.add(list.get(i).getPlayer());
        }
        this.setup = new GameSetup(players, this);
        this.flowCrlThread.start();
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        flowCtrl.setGameInput((NetMessage) arg);
    }

    public TurnSetupManager gettManager() {
        return tManager;
    }

    public void addPlayer(PlayerClient player){
        list.add(player);
    }

    public void removePlayer(PlayerClient player){
        list.remove(player);
    }

    public boolean isUsingCompleteRules(){
        return useCompleteRules;
    }

    public boolean isUsingCustomBonusTiles(){
        return useCustomBonusTiles;
    }

    public int getGameId(){
        return id;
    }

    public ArrayList<PlayerClient> getPlayerInGame(){
        return list;

    }

    public GameFlowController getGameFlowctrl(){
        return this.flowCtrl;
    }

}
