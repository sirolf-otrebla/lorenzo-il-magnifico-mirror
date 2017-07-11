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
import it.polimi.ingsw.ps05.server.controller.startactionstrategies.DoNothingStrategy;
import it.polimi.ingsw.ps05.server.controller.startactionstrategies.StartActionStrategyContainer;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 *  this class represents a Game, which is the representation of a single match. a single server
 *  can host multiple Games, thus the Game class must have direct references both to {@link PlayerClient} classes,
 *  which represents the connected clients, and to {@link Player} classes, which represent the Player object
 *  at model level. This Class can be considered as a bridge between the presenter / controller and the
 *  Model classes.
 */
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
    private BonusTileDraftController bonusTileDraftController;
    private DraftController draftController;
    private Thread draftControllerThread;
    private static final int MAX_LEADER_CARDS = 4;
    private boolean useCompleteRules = false;
    private boolean useCustomBonusTiles = false;
    public static final int FAM_DIM = 4;
    private StartActionStrategyContainer startActionStrategyContainer = new StartActionStrategyContainer(
            new DoNothingStrategy(this,false), this);
    private EndActionStrategyContainer endActionStrategyContainer;
    private Semaphore semStart;
    private int act_waiting_time_ms = 0;


    /** this is the main Game constructor.
     *
     * @param useCompleteRules   this is a boolean value that represent if the game is using complete
     *                           rules set.
     * @param useCustomBonusTiles this is a boolean value that represent if the game is using custom
     *                            bonus tiles
     * @param id                  this is a dynamic and unique id for the Game. Each game possess an ID.
     * @param clientList         this an arraylist of {@link PlayerClient}, which are going to enter in game
     */
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

    /**this method is called by the {@link Lobby} class, and it's goal is to build and
     * start a thread containing the entire game operations. in this way, full parallelism
     * between games can be achieved.
     *
     * @throws InterruptedException a thread exception thrown when the Game thread is improperly
     * interrupted
     */
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
        if (useCompleteRules){
        	draftController = new DraftController(new ArrayList<>(clientHashMap.values()), this);
            this.draftControllerThread = new Thread(draftController);
            this.draftControllerThread.start();
            draftControllerThread.join();
            System.out.println("post draft");
        }
        
        if (useCustomBonusTiles){
        	bonusTileDraftController = new BonusTileDraftController(new ArrayList<>(clientHashMap.values()), this);
        	this.draftControllerThread = new Thread(bonusTileDraftController);
        	this.draftControllerThread.start();
        	draftControllerThread.join();
        }
        System.out.println("Starting game flow");
        this.flowCrlThread.start();
    }

    /** this method is an Update method inherited by {@link Observer}, called when a {@link it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage}
     * arrives to {@link ServerNetMessageVisitor} instances. the message is then passed to {@link GameFlowController}
     * which is the Class that takes care of the Game flow.
     * @param o     this is the observable object that notifies the Game instance
     * @param arg   this argument in this implementation represents the {@link it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage}
     *              received by the Clients
     *              @see PlayerClient
     *              @see it.polimi.ingsw.ps05.server.net.NetMessageVisitor
     *              @see NetMessage
     */
    @Override
    public synchronized void update(Observable o, Object arg) {
        flowCtrl.setGameInput((NetMessage) arg);
    }


    /** this method returns the TurnSetupManager of this Game Instance
     * @see TurnSetupManager
     * @return
     */
    public TurnSetupManager gettManager() {
        return tManager;
    }



    public boolean isUsingCompleteRules(){
        return useCompleteRules;
    }

    public boolean isUsingCustomBonusTiles(){
        return useCustomBonusTiles;
    }

    /**
     *
     * @return this method returns the unique game ID of this Game instance
     */
    public int getGameId(){
        return id;
    }

    /** this method returns an Hashmap of players connected, which has as key the referenceID of a player,
     *  and as value the PlayerClient instance with that referenceID.
     * @return an {@code HashMap<Integer, PlayerClient>} representing the connected clients.
     */
    public HashMap<Integer, PlayerClient> getPlayerInGame(){
        return clientHashMap;

    }

    /**
     *
     * @return returns a reference tho the gameFlowController related to this game instance
     */
    public GameFlowController getGameFlowctrl(){
        return this.flowCtrl;
    }

    /**
     *
     * @return a list of Excommunication Cards used in this game
     */
    public ArrayList<ExcommunicationCard> getExcommList() {
        return excommList;
    }

    /**
     *
     * @return returns the player that's currently active (e.g. that can send and receive messages from server)
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /** sets the Game's Active Player
     * @see Player
     * @see PlayerClient
     * @param nextPlayer this is the player who is going to become active.
     */
    public void setActivePlayer(Player nextPlayer) {
    	if (activePlayer != null)
    		this.clientHashMap.get(activePlayer.getPlayerID()).setInactive();
            this.activePlayer = nextPlayer;
            this.clientHashMap.get(nextPlayer.getPlayerID()).setActive();
        
    }

    /**
     *
     * @return returns a reference to the game Board of this Game
     */
    public Board getBoard() {
    	return this.gBoard;
    }

    /**
     *
     * @return returns a reference to the Round object that is currently executed. A round object represents some
     * kind of state for the Game object.
     * @see Round
     */
    public Round getState() {
        return state;
    }

    /**
     * can set the Round object that's currently executed
     * @see Round
     * @param state Round object currently executed
     */
    public void setState(Round state) {
        this.state = state;
    }

    /**
     * @param id this is an integer that represents a {@link PlayerClient} referenceID
     * @return the {@link PlayerClient} with the same referenceID of the argument passed. returns {@code null}
     * if there's not a Client with that ReferenceID
     */
    public PlayerClient getPlayerClient(Integer id){
        return this.clientHashMap.get(id);
    }

    /**
     *
     * @return returns an instance to a {@link Semaphore} object that's used to stop the Game thread when
     * all clients are setupping their games. when all Clients have done, the semaphore is released.
     */
    public Semaphore getSemStart() {
        return semStart;
    }

    /**
     *
     * @return returns the DraftController object of this Game Instance
     */
    public DraftController getDraftController() {
        return draftController;
    }

    /** this method is used to set the game board externally. it is not safe to call this method when the
     * Game is running, but sometimes becomes necessary to modify the board at runtime
     *
     * @param gBoard the game board to be used by game
     */
    public void setgBoard(Board gBoard) {
        this.gBoard = gBoard;
    }

    /**
     *
     * @return returns an instance of the EndActionStrategyContainer, which is a container class used
     * to execute Strategies (as in the Strategy Pattern) during the game. in particular, this container
     * refers to those strategies that are meant to be executed at the end of a player action.
     */
    public EndActionStrategyContainer getEndActionStrategyContainer() {
        return endActionStrategyContainer;
    }

    /** this method set the possible Privilege Conversion Alternatives, which are generally Resources or
     * points (note that in this implementation Faith, military and victory points are
     * {@link Resource} subclasses
     *
     * @param list a list of Alternatives for Privilege convesion, represented as a Logical Sum of Products
     *             (first list represent a logical AND, second list represents a logical OR)
     *  @see Resource
     *  @see it.polimi.ingsw.ps05.model.resourcesandbonuses.PrivilegeBonus
     */
    public void setPrivilegeConvResAlternatives(ArrayList<ArrayList<Resource>> list){
    	this.privilegeConvResAlternatives = list;
    }

    /**
     *
     * @return returns the possible Privilege Conversion Alternatives, using a list of Alternatives for Privilege convesion,
     * represented as a Logical Sum of Product, first list represent a logical AND, second list represents a logical OR)
     */
    public ArrayList<ArrayList<Resource>> getPrivilegeConvResAlternatives() {
        return privilegeConvResAlternatives;
    }

    /**
     *
     * @return returns an integer representing the game timeout for a player action.
     */
	public int getAct_waiting_time_ms() {
		return act_waiting_time_ms;
	}

    /**
     *
     * @return returns the {@link BonusTileDraftController} related to this Game instance
     */
    public BonusTileDraftController getBonusTileDraftController() {
        return bonusTileDraftController;
    }

    /**
     *
     * @return an instance of the StartActionStrategyContainer, which is a container class used
     * to execute Strategies (as in the Strategy Pattern) during the game. in particular, this container
     * refers to those strategies that are meant to be executed at the Start of a player action.
     */
    public StartActionStrategyContainer getStartActionStrategyContainer() {
        return startActionStrategyContainer;
    }

    /**
     *
     * @param act_waiting_time_s set the duration of the timeout for a player action.
     */
    public void setAct_waiting_time_ms(int act_waiting_time_s) {
		this.act_waiting_time_ms = 1000*act_waiting_time_s;
	}
}

