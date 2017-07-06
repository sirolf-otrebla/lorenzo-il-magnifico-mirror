package it.polimi.ingsw.ps05.client.ctrl;

import it.polimi.ingsw.ps05.client.net.ClientMessageVisitor;
import it.polimi.ingsw.ps05.client.net.ServerInterface;
import it.polimi.ingsw.ps05.client.view.interfaces.ActionSpaceViewObject;
import it.polimi.ingsw.ps05.client.view.interfaces.HarvestSpaceViewObject;
import it.polimi.ingsw.ps05.client.view.interfaces.ProductionSpaceViewObject;
import it.polimi.ingsw.ps05.client.view.interfaces.TowerTileViewObject;
import it.polimi.ingsw.ps05.net.GameStatus;
import it.polimi.ingsw.ps05.net.message.GameSetupMessage;
import it.polimi.ingsw.ps05.net.message.NetMessage;

/**
 * Created by Alberto on 27/06/2017.
 */
public class Client {

    /* event observers */
    private MoveFamiliarListener moveFamiliarListener = new MoveFamiliarListener();
    private TakeCardListener takeCardListener = new TakeCardListener();
    /* end event observers */
    private static Client client;
    private ServerInterface serverInterface;
    private GameStatus gameStatus;
    private String username;
    private Integer id;
    private boolean inGame;
    private ClientMessageVisitor messageVisitor;
    private LoginController loginController;
    private ViewAdapter viewAdapter;

    private Client(){
        messageVisitor = new ClientMessageVisitor();
        loginController = new LoginController();
        //TODO ATTIVA LOBBY
        //TODO GESTISCE CONNESSIONE

    	getConnection();

    }

    private void getConnection(){
        this.serverInterface = ServerInterface.getInstance();
    }

    public static Client getInstance(){
        if (client== null)
            client = new Client();
        return client;
    }

    public void startGame(String viewType, GameSetupMessage message){
        try {
            ViewAdapter viewAdapter = ViewAdapter.createInstance(viewType);
            viewAdapter.setUpInterface(message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public void sendToServer(NetMessage message){
        this.serverInterface = ServerInterface.getInstance();
    	serverInterface.getConnection().send(message);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    
    public void setUsername(String username){
    	this.username = username;
    }
    
    public void setId(Integer id){
    	this.id = id;
    }
    
    public String getUsername(){
    	return username;
    }
    
    public Integer getId(){
    	return id;
    }

    public void linkToObserver(ActionSpaceViewObject observable){
        observable.addObserver(moveFamiliarListener);
    }

    public void linkToObserver(TowerTileViewObject observable){
        System.out.println("aggiungo observable");
        observable.addObserver(this.takeCardListener);
    }

    public void linkToObserver(HarvestSpaceViewObject observable){
    	//TODO
    }

    public void linkToObserver(ProductionSpaceViewObject observable){
    	//TODO
    }

    public void launchLoginForm(){
        //TODO
    }

    public ClientMessageVisitor getMessageTaker() {
        return messageVisitor;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
