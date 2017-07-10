package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.cards.ExcommunicationCard;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.Resource;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.net.message.NetMessage;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.EndActionStrategy;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.EndActionStrategyContainer;
import it.polimi.ingsw.ps05.server.controller.endactionstrategies.MoveNextPlayerStrategy;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Game implements Observer {

    private int id;
    private GameSetup setup;
    private  GameFlowController flowCtrl;
    public boolean end = false;
    private Thread flowCrlThread;private TurnSetupManager tManager;
    private HashMap<Integer,PlayerClient> clientHashMap;
    private ArrayList<ArrayList<Resource>> privilegeConvResAlternatives;
    private Board gBoard;
    private ArrayList<ExcommunicationCard> excommList;
    private Player activePlayer;
    private Round state;
    private DraftController draftController;
    private Thread draftControllerThread;
    private static final int MAX_LEADER_CARDS = 4;
    private boolean useCompleteRules = false;
    private boolean useCustomBonusTiles = false;
    public static final int FAM_DIM = 4;
    private EndActionStrategyContainer endActionStrategyContainer;
    private Semaphore semStart;
    private int act_waiting_time_ms = 0;


    public Game(boolean useCompleteRules, boolean useCustomBonusTiles, int id,
                ArrayList<PlayerClient> clientList){
        synchronized (this) {
            this.endActionStrategyContainer =
                    new EndActionStrategyContainer(new MoveNextPlayerStrategy(this), this);
            this.id = id;
            this.semStart = new Semaphore(0);
            this.useCompleteRules = useCompleteRules;
            this.useCustomBonusTiles = useCustomBonusTiles;
            this.clientHashMap = new HashMap<Integer, PlayerClient>();
            this.privilegeConvResAlternatives = new ArrayList<ArrayList<Resource>>();
            for (PlayerClient client : clientList) {
                clientHashMap.put(client.getId(), client);
                client.setInGame(this);
            }
        }
        this.excommList = new ArrayList<>();
    }

    public void start() throws InterruptedException {
        ArrayList<PlayerClient> playerClients = new ArrayList<PlayerClient>(clientHashMap.values());
        ArrayList<Player> players = new ArrayList<>();
        ColorEnumeration[] colorEnumerationAssignationArray = {
                ColorEnumeration.Green,
                ColorEnumeration.Yellow,
                ColorEnumeration.Blue,
                ColorEnumeration.Red
        };
        Collections.shuffle(playerClients);
        for (int i = 0; i < playerClients.size(); i++) {
            playerClients.get(i).BuildPlayer(colorEnumerationAssignationArray[i]);
            players.add(playerClients.get(i).getPlayer());
        }
        this.flowCtrl = new GameFlowController(this);
        this.flowCrlThread = new Thread(flowCtrl);
        this.setup = new GameSetup(players, this);
        this.gBoard = this.setup.getBoard();
        tManager = this.setup.getTurnSetupManager();
        for (PlayerClient client: this.clientHashMap.values()) {
            GameStatus status = new GameStatus( players,  this.gBoard, client.getPlayer(), GameStatus.NO_PLAYER_ACTIVE);
            client.sendMessage(new GameSetupMessage(status));
        }
        Server.getInstance().registerGame(this);
        // waiting to start
         semStart.acquire(clientHashMap.size());
        // starting
         System.out.println("Complete rules: " + useCompleteRules + "\tBonus Tile" + useCustomBonusTiles);
        if (useCompleteRules || useCustomBonusTiles){
        	draftController = new DraftController(new ArrayList<>(clientHashMap.values()), this);
            this.draftControllerThread = new Thread(draftController);
            this.draftControllerThread.start();
            draftControllerThread.join();
            System.out.println("post draft");


        }
        System.out.println("Starting game flow");
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
    	if (activePlayer != null)
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

    public Semaphore getSemStart() {
        return semStart;
    }

    public DraftController getDraftController() {
        return draftController;
    }

    public void setgBoard(Board gBoard) {
        this.gBoard = gBoard;
    }

    public EndActionStrategyContainer getEndActionStrategyContainer() {
        return endActionStrategyContainer;
    }
    
    public void setPrivilegeConvResAlternatives(ArrayList<ArrayList<Resource>> list){
    	this.privilegeConvResAlternatives = list;
    }

    public ArrayList<ArrayList<Resource>> getPrivilegeConvResAlternatives() {
        return privilegeConvResAlternatives;
    }

	public int getAct_waiting_time_ms() {
		return act_waiting_time_ms;
	}

	public void setAct_waiting_time_ms(int act_waiting_time_s) {
		this.act_waiting_time_ms = 1000*act_waiting_time_s;
	}
}

