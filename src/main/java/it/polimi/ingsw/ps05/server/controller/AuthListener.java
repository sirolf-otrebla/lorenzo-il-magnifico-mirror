package it.polimi.ingsw.ps05.server.controller;

import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.net.message.LoggedMessage;
import it.polimi.ingsw.ps05.net.message.LoginMessage;
import it.polimi.ingsw.ps05.net.message.RegisteredMessage;
import it.polimi.ingsw.ps05.net.message.RegistrationMessage;
import it.polimi.ingsw.ps05.server.database.Database;
import it.polimi.ingsw.ps05.server.net.PlayerClient;

/**
 * Created by Alberto on 02/07/2017.
 */
public class AuthListener {

    private PlayerClient client;

    public AuthListener(PlayerClient client){
        this.client = client;
    }
    public void visit(LoginMessage msg){

        if( Server.getInstance().getUserDatabase().checkUser(
                msg.getUsername(), msg.getPassword())){
            client.setUsername(msg.getUsername());
            System.out.println("inserisco nuovo client");
            int id = Database.getInstance().getIdForUsername(msg.getUsername());
            if (id >= 0){
            	this.client.setIdAfterLogin(id);
            }
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_LOGGED));
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
        } else {
            this.client.setLogged(false);
            client.sendMessage(new LoggedMessage(LoggedMessage.STATUS_FAILED_LOGIN));
        }
    }

    public void visit(RegistrationMessage msg){
        if (Server.getInstance().getUserDatabase().registerNewUser(
                msg.getUsername(), msg.getPassword())){
            client.setUsername(msg.getUsername());
            System.out.println("inserisco nuovo client");
            int id = Database.getInstance().getIdForUsername(msg.getUsername());
            if (id >= 0){
            	this.client.setIdAfterLogin(id);
            }
            Server.getInstance().putNewClient(client);
            this.client.setLogged(true);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_REGISTERED));
            client.setUsername(msg.getUsername());
        }else {
            this.client.setLogged(false);
            client.sendMessage(new RegisteredMessage(RegisteredMessage.STATUS_FAILED_REGIST));
        }
    }


}
