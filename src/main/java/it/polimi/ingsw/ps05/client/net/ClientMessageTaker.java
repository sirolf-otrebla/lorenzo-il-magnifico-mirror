package it.polimi.ingsw.ps05.client.net;

import it.polimi.ingsw.ps05.client.ctrl.AuthResponseVisitor;
import it.polimi.ingsw.ps05.client.ctrl.Client;
import it.polimi.ingsw.ps05.client.ctrl.LobbyMessageVisitor;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 03/07/2017.
 */
public class ClientMessageTaker implements Runnable, NetMessageVisitor {
    private NetMessage inputMessage;
    private Semaphore sem;

    public ClientMessageTaker(){
        sem = new Semaphore(0);

    }
    @Override
    public void visit(GameMessage msg) {

    }

    @Override
    public void visit(LobbyMessage msg) {
        System.out.println("sono nel primo visitor (lobbyMessage)");
        LobbyMessageVisitor visitor = new LobbyMessageVisitor();
        msg.acceptVisitor(visitor);


    }

    @Override
    public void visit(LeaderDraftMessage msg) {

    }

    @Override
    public void visit(AuthMessage msg) {

    }

    public void visit(AuthResponseMessage msg) {
       AuthResponseVisitor visitor =  new AuthResponseVisitor(Client.getInstance().getLoginController().getSemaphore());
       System.out.println("sono nel primo visitor (authResponse)");
       msg.acceptVisitor(visitor);
    }

    @Override
    public void visit(LobbyResponseMessage msg) {
        // no
    }


    @Override
    public void run() {
        while(true){
            try {
                sem.acquire();
                if(this.inputMessage == null) continue;
                System.out.println("ho ricevuto qualcosa di bellissimo dal server");
                this.inputMessage.acceptVisitor(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Semaphore getSem() {
        return sem;
    }

    public NetMessage getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(NetMessage inputMessage) {
        this.inputMessage = inputMessage;
    }
}