package it.polimi.ingsw.ps05.client.net;

import it.polimi.ingsw.ps05.client.ctrl.*;
import it.polimi.ingsw.ps05.net.message.*;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameMessage;
import it.polimi.ingsw.ps05.net.message.gamemessages.GameResponseMessage;
import it.polimi.ingsw.ps05.server.net.NetMessageVisitor;

import java.util.concurrent.Semaphore;

/**
 * Created by Alberto on 03/07/2017.
 */
public class ClientMessageVisitor implements Runnable, NetMessageVisitor {
    private NetMessage inputMessage;
    private Semaphore sem;

    public ClientMessageVisitor(){
        sem = new Semaphore(0);

    }

    @Override
    public void visit(GameResponseMessage msg) {
        GameResponseMessageVisitor visitor = new GameResponseMessageVisitor();
        msg.acceptVisitor(visitor);

    }

    @Override
    public void visit(GameMessage msg) {
        //todo errore

    }

    @Override
    public void visit(LobbyMessage msg) {
        System.out.println("sono nel primo visitor (lobbyMessage)");
        LobbyMessageVisitor visitor = new LobbyMessageVisitor();
        msg.acceptVisitor(visitor);


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
    public void visit(DraftMessage msg) {
    	System.out.println("Draft message visitor");
        DraftVisitor visitor = new DraftVisitor();
        msg.acceptVisitor(visitor);
    }

    @Override
    public void visit(DraftResponseNetMessage msg) {
            // no
    }


    @Override
    public void run() {
        while(true){
            try {
                sem.acquire();
                System.out.println("Ricevuto dal server, null?");
                if(this.inputMessage == null) continue;
                System.out.println("messaggio ricevuto non null");
                System.out.println(inputMessage.toString());
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
