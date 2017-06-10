package it.polimi.ingsw.ps05.net.server;

import it.polimi.ingsw.ps05.controller.GameFlowController;
import it.polimi.ingsw.ps05.controller.GameSetup;
import it.polimi.ingsw.ps05.model.Board;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.server.Message.NetMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Game implements Observer {

    private GameSetup setup;
    private final GameFlowController flowCtrl;
    public boolean end;
    private Thread flowCrlThread;

    private Board gBoard;


    public Game(ArrayList<PlayerClient> clientList){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < clientList.size(); i++) {
            clientList.get(i).BuildPlayer(ColorEnumeration.values()[i]);
            players.add(clientList.get(i).getPlayer());
        }
        this.setup = new GameSetup(players);
        this.flowCtrl = new GameFlowController(this);
        this.flowCrlThread = new Thread(flowCtrl);
        this.gBoard = this.setup.getBoard();

    }

    public void start(){
        this.flowCrlThread.start();
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        flowCtrl.setGameInput((NetMessage) arg);
    }
}
