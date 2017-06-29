package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Game implements Observer {

    private int id;
    private GameSetup setup;
    private  GameFlowController flowCtrl;
    public boolean end;
    private Thread flowCrlThread;
    private TurnSetupManager tManager;
    private HashMap<Integer,PlayerClient> clientHashMap;
    private Board gBoard;
    private ArrayList<ExcommunicationCard> excommList;
    private Player activePlayer;
    private Round state;


    private boolean useCompleteRules = true;
    private boolean useCustomBonusTiles = false;

    public static final int FAM_DIM = 4;


    public Game(boolean useCompleteRules, boolean useCustomBonusTiles, int id){
        this.id = id;
        this.useCompleteRules = useCompleteRules;
        this.useCustomBonusTiles = useCustomBonusTiles;
        this.clientHashMap = new HashMap<Integer, PlayerClient>();
        this.excommList = new ArrayList<>();

    }

    public void start(){

        this.flowCtrl = new GameFlowController(this);
        this.flowCrlThread = new Thread(flowCtrl);
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < clientHashMap.size(); i++) {
            clientHashMap.get(i).BuildPlayer(ColorEnumeration.values()[i]);
            players.add(clientHashMap.get(i).getPlayer());
        }
        this.setup = new GameSetup(players, this);
        this.gBoard = this.setup.getBoard();
        tManager = this.setup.getTurnSetup();
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
        clientHashMap.put(player.getId(), player);
        player.addObserver(this);
    }

    public void removePlayer(PlayerClient player){
        clientHashMap.remove(player);
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

    public HashMap<Integer, PlayerClient> getPlayerInGame(){
        return clientHashMap;

    }

    public GameFlowController getGameFlowctrl(){
        return this.flowCtrl;
    }

    public ArrayList<ExcommunicationCard> getExcommList() {
        return excommList;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player nextPlayer) {
        this.clientHashMap.get(activePlayer.getPlayerID()).setInactive();
        this.activePlayer = nextPlayer;
        this.clientHashMap.get(nextPlayer.getPlayerID()).setActive();
    }

    public Board getBoard() {
    	return this.gBoard;
    }

    public Round getState() {
        return state;
    }

    public void setState(Round state) {
        this.state = state;
    }

    public PlayerClient getPlayerClient(Integer id){
        return this.clientHashMap.get(id);
    }
}
